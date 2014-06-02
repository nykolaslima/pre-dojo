package br.com.preDojo.domain.model.match;

import java.util.Date;

public class Kill {

	private Player killer;
	private Player killed;
	private Weapon weapon;
	private Date killDate;
	
	private Kill(Builder builder) {
		killer = builder.killer;
		killed = builder.killed;
		weapon = builder.weapon;
		killDate = builder.killDate;
	}
	
	public static class Builder {
		private Player killer;
		private Player killed;
		private Weapon weapon;
		private Date killDate;
		
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
		
		public Builder at(Date killDate) {
			this.killDate = killDate;
			return this;
		}
		
		public Kill build() {
			return new Kill(this);
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
	public Date getKillDate() {
		return killDate;
	}
	
}
