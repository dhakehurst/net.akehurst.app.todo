package net.akehurst.app.todo.engineering.user2Gui;

import net.akehurst.application.framework.technology.interfaceGui.IGuiScene;
import net.akehurst.application.framework.technology.interfaceGui.data.table.IGuiTable;
import net.akehurst.application.framework.technology.interfaceGui.elements.IGuiElement;
import net.akehurst.application.framework.technology.interfaceGui.elements.IGuiText;

public interface IHomeScene extends IGuiScene {

	IGuiText getUsername();

	IGuiElement getActionSignOut();

	IGuiTable getTableAvailableLists();

	IGuiElement getActionCreateNewList();

}
