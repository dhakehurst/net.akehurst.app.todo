package net.akehurst.app.todo.computational.interfaceUser.data;

import java.util.List;

import net.akehurst.application.framework.common.annotations.declaration.Composite;
import net.akehurst.application.framework.common.annotations.declaration.DataType;



@DataType
public interface ToDoList {

	ToDoListOwner getOwner();
	
	ToDoListIdentity getId();
	
	@Composite(opposite="list")
	List<ToDoListItem> getItems();
}
