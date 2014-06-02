package br.com.preDojo.application.match.entry;

import java.util.Date;

import br.com.preDojo.domain.model.match.Type;

public abstract class MatchEntry {

	public static Type[] GAME_TYPES = {Type.MATCH_START, Type.MATCH_END};
	public static Type[] PLAYER_TYPES = {Type.PLAYER_KILL};
	
	private Date creationDate;
	private Type type;
	
	public MatchEntry(Date creationDate, Type type) {
		this.creationDate = creationDate;
		this.type = type;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public Type getType() {
		return type;
	}
	
}
