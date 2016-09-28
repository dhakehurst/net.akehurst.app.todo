package net.akehurst.app.todo.application.desktop;

import net.akehurst.app.todo.computational.todo.ToDo;
import net.akehurst.app.todo.engineering.listStore2Persist.ListStoreToPersist;
import net.akehurst.app.todo.engineering.user2Gui.UserToGui;
import net.akehurst.application.framework.common.annotations.instance.ComponentInstance;
import net.akehurst.application.framework.common.annotations.instance.ServiceInstance;
import net.akehurst.application.framework.engineering.authenticator2Gui.AuthenticatorToGui;
import net.akehurst.application.framework.realisation.AbstractApplication;
import net.akehurst.application.framework.service.authorisation.ConfigFileAuthorisationService;
import net.akehurst.application.framework.technology.filesystem.StandardFilesystem;
import net.akehurst.application.framework.technology.gui.jfx.JfxWindow;
import net.akehurst.application.framework.technology.log4j.Log4JLogger;
import net.akehurst.application.framework.technology.persistence.filesystem.HJsonFile;
import net.akehurst.application.framework.technology.persistence.jdo.JdoPersistence;

public class ToDoDesktopApplication extends AbstractApplication {

	public ToDoDesktopApplication(final String id) {
		super(id);
	}

	// --- Services ---
	@ServiceInstance
	Log4JLogger logger;

	@ServiceInstance
	StandardFilesystem fs;

	@ServiceInstance
	HJsonFile configuration;

	@ServiceInstance
	ConfigFileAuthorisationService authorisation;

	// --- Computational ---
	@ComponentInstance
	ToDo todo;

	@ComponentInstance
	UserToGui userToGui;

	@ComponentInstance
	AuthenticatorToGui authenticatorToGui;

	@ComponentInstance
	ListStoreToPersist listStoreToPersist;

	@ComponentInstance
	JfxWindow gui;

	@ComponentInstance
	JdoPersistence db;

	@Override
	public void afConnectParts() {
		this.todo.portUserInterface().connect(this.userToGui.portUserInterface());
		this.todo.portAuth().connect(this.authenticatorToGui.portAuth());
		this.todo.portStore().connect(this.listStoreToPersist.portStore());

		this.userToGui.portGui().connect(this.gui.portGui());
		this.authenticatorToGui.portGui().connect(this.gui.portGui());
		this.listStoreToPersist.portPersist().connect(this.db.portPersist());
	}
}