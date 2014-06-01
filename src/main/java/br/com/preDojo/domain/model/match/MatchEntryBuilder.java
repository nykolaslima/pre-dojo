package br.com.preDojo.domain.model.match;

import java.util.Date;

public class MatchEntryBuilder<T> {

	private Date creationDate;
	private Type type;
	
	@SuppressWarnings("unchecked")
	public T createdAt(Date creationDate) {
		this.creationDate = creationDate;
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T withType(Type type) {
		this.type = type;
		return (T) this;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public Type getType() {
		return type;
	}
	
}
