package br.com.preDojo.domain.model.match;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Kill> kills;

	public void addKill(Kill kill) {
		if(kills == null) {
			kills = new ArrayList<Kill>();
		}
		
		kills.add(kill);
	}
	
	public int getKillsCount() {
		if(kills == null) return 0;
		
		return kills.size();
	}
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
