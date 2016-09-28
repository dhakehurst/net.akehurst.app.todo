package net.akehurst.app.todo.engineering.user2Gui;

import net.akehurst.application.framework.technology.interfaceGui.IGuiScene;
import net.akehurst.application.framework.technology.interfaceGui.elements.IGuiElement;
import net.akehurst.application.framework.technology.interfaceGui.elements.IGuiText;

public interface IWelcomeScene extends IGuiScene {

	IGuiText getWelcomeMessage();

	IGuiElement getActionSignIn();
}
