package net.akehurst.app.todo.computational.interfaceUser;

import net.akehurst.application.framework.common.AbstractDataType;

public class ToDoListIdentity extends AbstractDataType {
	
	public ToDoListIdentity(String value) {
		super(value);
		this.value = value;
	}

	String value;

	public String getValue() {
		return this.value;
	}
}
