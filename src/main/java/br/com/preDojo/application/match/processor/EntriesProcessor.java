package br.com.preDojo.application.match.processor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.com.preDojo.application.match.entry.MatchEntry;
import br.com.preDojo.application.match.entry.Type;
import br.com.preDojo.application.match.entry.game.GameEntry;
import br.com.preDojo.application.match.entry.player.PlayerEntry;
import br.com.preDojo.domain.model.match.Kill;
import br.com.preDojo.domain.model.match.Match;

import com.google.common.collect.Iterables;

public class EntriesProcessor {

	private final MatchProcessor matchProcessor;
	
	public EntriesProcessor(MatchProcessor matchProcessor) {
		this.matchProcessor = matchProcessor;
	}

	public Set<Match> processEntries(List<MatchEntry> matchEntries) {
		Set<Match> matches = new LinkedHashSet<Match>();
		
		for(MatchEntry matchEntry : matchEntries) {
			if(matchEntry.getType() == Type.MATCH_START) {
				matches.add(matchProcessor.processEntry((GameEntry) matchEntry));
			} else if(matchEntry.getType() == Type.PLAYER_KILL) {
				Match currentMatch = Iterables.getLast(matches);
				PlayerEntry playerEntry = (PlayerEntry) matchEntry;
				
				currentMatch.addPlayer(playerEntry.getKiller());
				currentMatch.addPlayer(playerEntry.getKilled());
				
				Kill kill = new Kill.Builder()
							.killer(playerEntry.getKiller())
							.killed(playerEntry.getKilled())
							.withWeapon(playerEntry.getWeapon())
							.at(playerEntry.getCreationDate())
							.build();
				currentMatch.addKill(kill);				
			}
		}
		
		return matches;
	}
	
}
