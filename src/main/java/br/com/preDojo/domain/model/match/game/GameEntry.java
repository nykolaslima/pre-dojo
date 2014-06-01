package br.com.preDojo.domain.model.match.game;

import br.com.preDojo.domain.model.match.MatchEntry;
import br.com.preDojo.domain.model.match.MatchEntryBuilder;

public class GameEntry extends MatchEntry {

	private long id;

	private GameEntry(Builder builder) {
		super(builder.getCreationDate(), builder.getType());
		id = builder.id;
	}
	
	public static class Builder extends MatchEntryBuilder<Builder> {
		
		private long id;
		
		public Builder withId(long id) {
			this.id = id;
			return this;
		}
		
		public GameEntry build() {
			return new GameEntry(this);
		}
		
	}
	
	public long getId() {
		return id;
	}
	
}
