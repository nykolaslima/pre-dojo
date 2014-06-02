package br.com.preDojo.domain.model.match;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class Match {

	private Date startDate;
	private Date endDate;
	private Set<Player> players;

	public Match(Date startDate) {
		this.startDate = startDate;
	}

	public void addKill(Kill kill) {
		getPlayer(kill.getKiller()).addKill(kill);
	}
	
	public void addPlayer(Player player) {
		if(players == null) {
			players = new HashSet<Player>();
		}
		
		players.add(player);
	}
	
	public Player getPlayer(final Player player) {
		if(players == null) return null;
		
		return Iterables.find(players, new Predicate<Player>() {
			@Override
			public boolean apply(Player p) {
				return p.equals(player);
			}
		});
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public Set<Player> getPlayers() {
		return players;
	}

}
