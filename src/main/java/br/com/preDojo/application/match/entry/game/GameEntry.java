package br.com.preDojo.application.match.entry.game;

import java.util.Date;

import br.com.preDojo.application.match.entry.MatchEntry;
import br.com.preDojo.application.match.entry.MatchEntryBuilder;
import br.com.preDojo.application.match.entry.Type;

public class GameEntry extends MatchEntry {

	private long id;

	// hack to Fixture-Factory work
	private GameEntry(Date creationDate, Type type) {
		super(creationDate, type);
	}
	
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
