package br.com.preDojo.application.mapper.match;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.preDojo.domain.model.match.MatchEntry;
import br.com.preDojo.domain.model.match.Type;
import br.com.preDojo.domain.model.match.game.GameEntry;
import br.com.preDojo.domain.model.match.player.Player;
import br.com.preDojo.domain.model.match.player.PlayerEntry;
import br.com.preDojo.domain.model.match.player.Weapon;

public class MatchEntryMapper {

	private static final String GAME_ID_PATTERN = "[mM]atch \\d+";
	private static final String USER_KILL_PATTERN = ".[^-]+- [a-zA-Z0-9<>]+ killed [a-zA-Z0-9<>]+ (by|using) .+";
	private static final String MATCH_STARTED_PATTERN = ".[^-]+- New match \\d+ has started";
	private static final String MATCH_ENDED_PATTERN = ".[^-]+- Match \\d+ has ended";
	private static final String CREATION_DATE_PATTERN = "\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}";
	private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";
	private static final String KILLER_PLAYER_PATTERN = "[a-zA-Z0-9<>]+ killed";
	private static final String KILLED_PLAYER_PATTERN = "killed [a-zA-Z0-9<>]+";
	private static final String WEAPON_PATTERN = "(using|by) [a-zA-Z0-9]+";

	public MatchEntry apply(String logEntry) {
		Type type = getType(logEntry);

		if(isGameEntry(type)) {
			return buildGameEntry(logEntry, type);
		} else if(isUserEntry(type)) {
			return buildPlayerEntry(logEntry, type);
		}
		
		throw new IllegalArgumentException(String.format("This type of log entry is not supported {%s}", type.name()));
	}

	private MatchEntry buildPlayerEntry(String logEntry, Type type) {
		return new PlayerEntry.Builder()
						.withType(type)
						.createdAt(getCreationDate(logEntry))
						.killer(getPlayer(PlayerType.KILLER, logEntry))
						.killed(getPlayer(PlayerType.KILLED, logEntry))
						.withWeapon(getWeapon(logEntry))
						.build();
	}

	private MatchEntry buildGameEntry(String logEntry, Type type) {
		return new GameEntry.Builder()
						.withType(type)
						.createdAt(getCreationDate(logEntry))
						.withId(getGameId(logEntry))
						.build();
	}
	
	private boolean isUserEntry(Type type) {
		return Arrays.asList(MatchEntry.PLAYER_TYPES).contains(type);
	}

	private boolean isGameEntry(Type type) {
		return Arrays.asList(MatchEntry.GAME_TYPES).contains(type);
	}

	private Type getType(String logEntry) {
		if(logEntry.matches(MATCH_STARTED_PATTERN)) {
			return Type.MATCH_START;
		} else if(logEntry.matches(MATCH_ENDED_PATTERN)) {
			return Type.MATCH_END;
		} else if(logEntry.matches(USER_KILL_PATTERN)) {
			return Type.PLAYER_KILL;
		} else {
			throw new IllegalArgumentException(String.format("Type for this log entry cannot be defined {%s}", logEntry));
		}
	}
	
	private Date getCreationDate(String logEntry) {
		Pattern pattern = Pattern.compile(CREATION_DATE_PATTERN);
		Matcher matcher = pattern.matcher(logEntry);
		if(matcher.find()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dateFormat.parse(matcher.group());
			} catch (ParseException e) {
				throw new IllegalArgumentException(String.format("Problem parsing log entry date {%s}", matcher.group()));
			}
		} else {
			throw new IllegalArgumentException(String.format("Date for this log entry cannot be defined {%s}", logEntry));
		}
	}
	
	private Player getPlayer(PlayerType playerType, String logEntry) {
		Pattern pattern = Pattern.compile(getPlayerTypePattern(playerType));
		Matcher matcher = pattern.matcher(logEntry);
		if(matcher.find()) {
			String playerName = matcher.group().replaceAll("\\s?killed\\s?", "");
			if(playerName.equals(PlayerEntry.WORLD_KILLER)) return null;
			
			return new Player(playerName);
		}
		
		throw new IllegalArgumentException(String.format("%s for this log entry cannot be defined {%s}", playerType.name().toLowerCase(), logEntry));
	}
	
	private String getPlayerTypePattern(PlayerType playerType) {
		if(playerType == PlayerType.KILLER) {
			return KILLER_PLAYER_PATTERN;
		} else {
			return KILLED_PLAYER_PATTERN;
		}
	}
	
	private Weapon getWeapon(String logEntry) {
		Pattern pattern = Pattern.compile(WEAPON_PATTERN);
		Matcher matcher = pattern.matcher(logEntry);
		if(matcher.find()) {
			String weaponName = matcher.group().replaceAll("(using|by)\\s", "");
			return new Weapon(weaponName);
		}
		
		throw new IllegalArgumentException(String.format("Weapon for this log entry cannot be defined {%s}", logEntry));
	}
	
	private enum PlayerType {
		KILLER, KILLED
	}
	
	private long getGameId(String logEntry) {
		Pattern pattern = Pattern.compile(GAME_ID_PATTERN);
		Matcher matcher = pattern.matcher(logEntry);
		if(matcher.find()) {
			return Long.parseLong(matcher.group().replaceAll("[mM]atch\\s", ""));
		}
		
		throw new IllegalArgumentException(String.format("Game ID for this log entry cannot be defined {%s}", logEntry));
	}

}
