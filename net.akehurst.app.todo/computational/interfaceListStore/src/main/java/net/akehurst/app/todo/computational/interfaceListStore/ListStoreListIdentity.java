package net.akehurst.app.todo.computational.interfaceListStore;

import net.akehurst.application.framework.common.AbstractDataType;

public class ListStoreListIdentity extends AbstractDataType {
	
	public ListStoreListIdentity(String value) {
		super(value);
		this.value = value;
	}

	String value;

	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}