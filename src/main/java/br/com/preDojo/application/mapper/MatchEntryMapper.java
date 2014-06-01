package br.com.preDojo.application.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import br.com.preDojo.application.mapper.pattern.PatternEvaluator;
import br.com.preDojo.application.mapper.pattern.field.CreationDatePattern;
import br.com.preDojo.application.mapper.pattern.field.GameIdPattern;
import br.com.preDojo.application.mapper.pattern.field.KilledPlayerPattern;
import br.com.preDojo.application.mapper.pattern.field.KillerPattern;
import br.com.preDojo.application.mapper.pattern.field.WeaponPattern;
import br.com.preDojo.domain.model.match.MatchEntry;
import br.com.preDojo.domain.model.match.Type;
import br.com.preDojo.domain.model.match.game.GameEntry;
import br.com.preDojo.domain.model.match.player.Player;
import br.com.preDojo.domain.model.match.player.PlayerEntry;
import br.com.preDojo.domain.model.match.player.Weapon;

public class MatchEntryMapper {

	private static final String USER_KILL_PATTERN = ".[^-]+- [a-zA-Z0-9<>]+ killed [a-zA-Z0-9<>]+ (by|using) .+";
	private static final String MATCH_STARTED_PATTERN = ".[^-]+- New match \\d+ has started";
	private static final String MATCH_ENDED_PATTERN = ".[^-]+- Match \\d+ has ended";
	private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";
	
	private final PatternEvaluator patternEvaluator;
	

	public MatchEntryMapper(PatternEvaluator patternEvaluator) {
		this.patternEvaluator = patternEvaluator;
	}

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
						.killer(getKiller(logEntry))
						.killed(getKilled(logEntry))
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
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			String creationDate = patternEvaluator.evaluatePattern(new CreationDatePattern(), logEntry);
			return dateFormat.parse(creationDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("Problem parsing log entry date {%s}", logEntry));
		}
	}
	
	private Player getKiller(String logEntry) {
		String killerName = patternEvaluator.evaluatePattern(new KillerPattern(), logEntry);
		
		if(killerName.equals(PlayerEntry.WORLD_KILLER)) return null;
		
		return new Player(killerName);
	}
	
	private Player getKilled(String logEntry) {
		String killedName = patternEvaluator.evaluatePattern(new KilledPlayerPattern(), logEntry);
		return new Player(killedName);
	}
	
	private Weapon getWeapon(String logEntry) {
		String weaponName = patternEvaluator.evaluatePattern(new WeaponPattern(), logEntry);
		return new Weapon(weaponName);
	}
	
	private long getGameId(String logEntry) {
		return Long.parseLong(patternEvaluator.evaluatePattern(new GameIdPattern(), logEntry));
	}
	
}
