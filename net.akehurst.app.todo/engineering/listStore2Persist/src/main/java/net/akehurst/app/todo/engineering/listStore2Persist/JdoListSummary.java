package net.akehurst.app.todo.engineering.listStore2Persist;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class JdoListSummary {

	String id;

	@Persistent
	@PrimaryKey
	public String getId() {
		return this.id;
	}

	public void setId(final String value) {
		this.id = value;
	}

	String owner;

	@Persistent
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(final String value) {
		this.owner = value;
	}

	String description;

	@Persistent
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String value) {
		this.description = value;
	}
}
