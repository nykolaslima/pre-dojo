package br.com.preDojo.domain.model.match;

import java.util.Date;

public abstract class MatchEntry {

	private Date creationDate;
	private Type type;
	
	public Date getCreationDate() {
		return creationDate;
	}
	public Type getType() {
		return type;
	}
	
}
