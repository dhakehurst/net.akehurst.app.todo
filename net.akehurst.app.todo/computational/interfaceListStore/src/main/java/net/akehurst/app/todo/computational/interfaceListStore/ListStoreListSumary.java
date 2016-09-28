package net.akehurst.app.todo.computational.interfaceListStore;

import net.akehurst.application.framework.common.AbstractDataType;

public class ListStoreListSumary extends AbstractDataType {

	public ListStoreListSumary(ListStoreOwner owner, ListStoreListIdentity id, String description) {
		super(owner,id);
		this.owner = owner;
		this.id = id;
		this.description = description;
	}
	
	ListStoreOwner owner;
	public ListStoreOwner getOwner() {
		return this.owner;
	}
	
	ListStoreListIdentity id;
	public ListStoreListIdentity getId() {
		return id;
	}
	
	String description;
	public String getDescription() {
		return this.description;
	}
	
}