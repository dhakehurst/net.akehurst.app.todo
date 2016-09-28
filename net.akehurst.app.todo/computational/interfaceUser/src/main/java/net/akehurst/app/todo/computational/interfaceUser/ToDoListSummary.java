package net.akehurst.app.todo.computational.interfaceUser;

import net.akehurst.application.framework.common.AbstractDataType;

public class ToDoListSummary extends AbstractDataType {

	public ToDoListSummary(ToDoListIdentity id, String description) {
		super(id);
		this.id = id;
		this.description = description;
	}
	
	ToDoListIdentity id;
	public ToDoListIdentity getId() {
		return id;
	}
	
	String description;
	public String getDescription() {
		return this.description;
	}
}
