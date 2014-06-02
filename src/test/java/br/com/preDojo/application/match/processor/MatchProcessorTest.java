package br.com.preDojo.application.match.processor;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.preDojo.application.match.entry.MatchEntry;
import br.com.preDojo.application.match.entry.game.GameEntry;
import br.com.preDojo.application.match.entry.player.PlayerEntry;
import br.com.preDojo.domain.model.match.Match;
import br.com.preDojo.domain.model.match.Player;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class MatchProcessorTest {

	private EntriesProcessor matchProcessor;
	
	@BeforeClass
	public static void setUpClass() {
		FixtureFactoryLoader.loadTemplates("br.com.preDojo.infrastructure.fixture");
	}
	
	@Before
	public void setUp() {
		matchProcessor = new EntriesProcessor(new MatchProcessor());
	}
	
	@Test
	public void shouldCreateMatchWithStartDateBasedOnStartMatchGameEntry() {
		GameEntry gameEntry = Fixture.from(GameEntry.class).gimme("matchStart");
		List<MatchEntry> entries = buildEntries(gameEntry);
		
		Match match = getFirstMatch(matchProcessor.processEntries(entries));
		
		assertEquals(gameEntry.getCreationDate(), match.getStartDate());
	}
	
	@Test
	public void shouldAddKillerAndKilledPlayerInMatchBasedOnPlayerEntry() {
		GameEntry gameEntry = Fixture.from(GameEntry.class).gimme("matchStart");
		PlayerEntry playerEntry = Fixture.from(PlayerEntry.class).gimme("valid");
		List<MatchEntry> entries = buildEntries(gameEntry, playerEntry);
		
		Match match = getFirstMatch(matchProcessor.processEntries(entries));
		
		assertThat(match.getPlayers(), contains(playerEntry.getKiller(), playerEntry.getKilled()));
	}
	
	@Test
	public void shouldCount1KillToKiller() {
		GameEntry gameEntry = Fixture.from(GameEntry.class).gimme("matchStart");
		PlayerEntry playerEntry = Fixture.from(PlayerEntry.class).gimme("valid");
		List<MatchEntry> entries = buildEntries(gameEntry, playerEntry);
		
		Match match = getFirstMatch(matchProcessor.processEntries(entries));
		Player player = match.getPlayer(playerEntry.getKiller());
		
		assertEquals(1, player.getKillsCount());
	}
	
	private Match getFirstMatch(Set<Match> matches) {
		Iterator<Match> iterator = matches.iterator();
		if(iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}
	
	private List<MatchEntry> buildEntries(MatchEntry... entries) {
		return Arrays.asList(entries);
	}
	
}
