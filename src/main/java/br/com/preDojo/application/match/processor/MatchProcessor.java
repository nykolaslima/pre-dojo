package br.com.preDojo.application.match.processor;

import br.com.preDojo.application.match.entry.game.GameEntry;
import br.com.preDojo.domain.model.match.Match;

public class MatchProcessor {

	public Match processEntry(GameEntry gameEntry) {
		return new Match(gameEntry.getCreationDate());
	}
	
}
