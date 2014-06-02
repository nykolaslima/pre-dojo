package br.com.preDojo.application.match.mapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.preDojo.application.match.entry.MatchEntry;
import br.com.preDojo.application.match.entry.Type;
import br.com.preDojo.application.match.entry.game.GameEntry;
import br.com.preDojo.application.match.entry.player.PlayerEntry;
import br.com.preDojo.application.match.mapper.MatchEntryMapper;
import br.com.preDojo.application.match.mapper.pattern.PatternEvaluator;
import br.com.preDojo.domain.model.match.Player;
import br.com.preDojo.domain.model.match.Weapon;

public class MatchEntryMapperTest {

	private MatchEntryMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new MatchEntryMapper(new PatternEvaluator());
	}
	
	@Test
	public void shouldTransformMatchStartEntry() {
		String logEntry = "23/04/2013 15:34:22 - New match 11348965 has started";
		MatchEntry matchEntry = mapper.apply(logEntry);
		assertEquals(Type.MATCH_START, matchEntry.getType());
	}
	
	@Test
	public void shouldTransformMatchEndEntry() {
		String logEntry = "23/04/2013 15:39:22 - Match 11348965 has ended";
		MatchEntry matchEntry = mapper.apply(logEntry);
		assertEquals(Type.MATCH_END, matchEntry.getType());
	}
	
	@Test
	public void shouldTransformPlayerKillEntry() {
		String logEntry = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		MatchEntry matchEntry = mapper.apply(logEntry);
		assertEquals(Type.PLAYER_KILL, matchEntry.getType());
	}
	
	@Test
	public void shouldTransformWorldKillEntry() {
		String logEntry = "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN";
		MatchEntry matchEntry = mapper.apply(logEntry);
		assertEquals(Type.PLAYER_KILL, matchEntry.getType());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfTypeCannotBeDefined() {
		String invalidLogEntry = "23/04/2013 15:36:33 - Nykolas said hello to Jonny";
		mapper.apply(invalidLogEntry);
	}
	
	@Test
	public void shouldTransformEntryDate() throws ParseException {
		String dateString = "23/04/2013 15:39:22";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date creationDate = dateFormat.parse(dateString);
		String logEntry = String.format("%s - Match 11348965 has ended", dateString);;
		
		MatchEntry matchEntry = mapper.apply(logEntry);
		
		assertEquals(creationDate, matchEntry.getCreationDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForBadDateFormat() {
		String invalidLogEntry = "2013/04/23 15:36:33 - <WORLD> killed Nick by DROWN";
		mapper.apply(invalidLogEntry);
	}
	
	@Test
	public void shouldTrasformPlayerKiller() {
		String logEntry = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Player killer = new Player("Roman");
		PlayerEntry playerEntry = (PlayerEntry) mapper.apply(logEntry);
		assertEquals(killer, playerEntry.getKiller());
	}
	
	@Test
	public void shouldTrasformPlayerKilled() {
		String logEntry = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Player killed = new Player("Nick");
		PlayerEntry playerEntry = (PlayerEntry) mapper.apply(logEntry);
		assertEquals(killed, playerEntry.getKilled());
	}
	
	@Test
	public void shouldTransformPlayerKillWeapon() {
		String logEntry = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Weapon weapon = new Weapon("M16");
		PlayerEntry playerEntry = (PlayerEntry) mapper.apply(logEntry);
		assertEquals(weapon, playerEntry.getWeapon());
	}
	
	@Test
	public void killerShouldBeNullIfWorldIsTheKiller() {
		String logEntry = "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN";
		PlayerEntry playerEntry = (PlayerEntry) mapper.apply(logEntry);
		assertNull(playerEntry.getKiller());
	}
	
	@Test
	public void shouldTransformWeaponWhenItsWorldWeapon() {
		String logEntry = "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN";
		Weapon weapon = new Weapon("DROWN");
		PlayerEntry playerEntry = (PlayerEntry) mapper.apply(logEntry);
		assertEquals(weapon, playerEntry.getWeapon());
	}
	
	@Test
	public void shouldTransformGameIdForStartedMatch() {
		long gameId = 11348965;
		String logEntry = String.format("23/04/2013 15:34:22 - New match %d has started", gameId);
		GameEntry gameEntry = (GameEntry) mapper.apply(logEntry);
		assertEquals(gameId, gameEntry.getId());
	}
	
}
