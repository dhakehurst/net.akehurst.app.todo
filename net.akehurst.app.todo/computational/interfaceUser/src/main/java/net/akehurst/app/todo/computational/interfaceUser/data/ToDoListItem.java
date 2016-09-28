package net.akehurst.app.todo.computational.interfaceUser.data;

import java.util.List;

import net.akehurst.application.framework.common.annotations.declaration.DataType;
import net.akehurst.application.framework.common.annotations.declaration.Reference;

@DataType
public interface ToDoListItem {

	@Reference(value="id", opposite="items")
	ToDoList getList();
	
	ToDoListItemIdentity getId();
	
	String getDescription();
	void setDescription(String value);
	
	@Reference(value="id", opposite="preceeds")
	List<ToDoListItem> getDependendsOn();
	
	@Reference(value="id", opposite="dependsOn")
	List<ToDoListItem> getPreceeds();
}
