package br.com.preDojo.domain.model.match.player;

import br.com.preDojo.domain.model.match.MatchEntry;
import br.com.preDojo.domain.model.match.MatchEntryBuilder;

public class PlayerEntry extends MatchEntry {

	public static String WORLD_KILLER = "<WORLD>";
	
	private Player killer;
	private Player killed;
	private Weapon weapon;
	
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
