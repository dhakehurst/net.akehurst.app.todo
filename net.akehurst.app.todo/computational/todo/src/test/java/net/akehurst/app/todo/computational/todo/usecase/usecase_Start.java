package net.akehurst.app.todo.computational.todo.usecase;

import org.junit.Test;

import net.akehurst.app.todo.computational.interfaceUser.IUserHomeNotification;
import net.akehurst.app.todo.computational.interfaceUser.IUserWelcomeNotification;
import net.akehurst.app.todo.computational.todo.ToDo;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.components.test.MockComponentPort;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;

public class usecase_Start {

	@Test
	public void requestStart() {

		final ToDo sut = new ToDo("sut");
		final MockComponentPort mockGui = new MockComponentPort();
		mockGui.connect(sut.portUserInterface());

		// user -> start
		final UserSession session = new UserSession("test", null);
		// sut.portUserInterface().getProvided(IUserWelcomeRequest.class).requestStart(session);

		// system -> welcomeMessage
		mockGui.expect(IUserWelcomeNotification.class, "notifyWelcomeMessage");

		// user -> login
		final String username = "test";
		final String password = "test";
		// sut.portUserInterface().getProvided(IUserAuthenticationRequest.class).requestLogin(session, username, password);

		// system -> authentication success
		mockGui.expect(IUserAuthenticationNotification.class, "notifyAuthenticationSuccess");

		// system -> show summary of available ToDoLists
		mockGui.expect(IUserHomeNotification.class, "notifyAuthenticationSuccess");
	}

}
