package br.com.preDojo.domain.model.match.user;

import br.com.preDojo.domain.model.match.MatchEntry;

public class UserEntry extends MatchEntry {

	private User killer;
	private User killed;
	private Weapon weapon;
	
	public User getKiller() {
		return killer;
	}
	public User getKilled() {
		return killed;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	
}
