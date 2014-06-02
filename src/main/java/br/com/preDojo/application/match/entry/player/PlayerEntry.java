package br.com.preDojo.application.match.entry.player;

import java.util.Date;

import br.com.preDojo.application.match.entry.MatchEntry;
import br.com.preDojo.application.match.entry.MatchEntryBuilder;
import br.com.preDojo.application.match.entry.Type;
import br.com.preDojo.domain.model.match.Player;
import br.com.preDojo.domain.model.match.Weapon;

public class PlayerEntry extends MatchEntry {

	public static String WORLD_KILLER = "<WORLD>";
	
	private Player killer;
	private Player killed;
	private Weapon weapon;
	
	// hack to Fixture-Factory work
	private PlayerEntry(Date creationDate, Type type) {
		super(creationDate, type);
	}
	
	private PlayerEntry(Builder builder) {
		super(builder.getCreationDate(), builder.getType());
		killer = builder.killer;
		killed = builder.killed;
		weapon = builder.weapon;
	}

	public static class Builder extends MatchEntryBuilder<Builder> {
		private Player killer;
		private Player killed;
		private Weapon weapon;
		
		public Builder killer(Player killer) {
			this.killer = killer;
			return this;
		}
		
		public Builder killed(Player killed) {
			this.killed = killed;
			return this;
		}
		
		public Builder withWeapon(Weapon weapon) {
			this.weapon = weapon;
			return this;
		}
		
		public PlayerEntry build() {
			return new PlayerEntry(this);
		}
	}
	
	public Player getKiller() {
		return killer;
	}
	public Player getKilled() {
		return killed;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	
}
