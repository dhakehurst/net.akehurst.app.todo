package net.akehurst.app.todo.computational.todo.test;

import org.junit.Test;

import net.akehurst.app.todo.computational.interfaceUser.IUserWelcomeNotification;
import net.akehurst.app.todo.computational.todo.ToDo;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.components.test.MockComponentPort;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorRequest;

public class test_ToDo {

	@Test
	public void requestStart() {

		final ToDo sut = new ToDo("sut");
		final MockComponentPort mockGui = new MockComponentPort();
		mockGui.connect(sut.portUserInterface());

		final UserSession session = new UserSession("test", null);
		// sut.portUserInterface().getProvided(IUserWelcomeRequest.class).requestStart(session);

		mockGui.expect(IUserWelcomeNotification.class, "notifyWelcomeMessage");

	}

	@Test
	public void requestLogin() {
		final ToDo sut = new ToDo("sut");
		final MockComponentPort mockGui = new MockComponentPort();
		mockGui.connect(sut.portUserInterface());

		final UserSession session = new UserSession("test", null);
		final String username = "test";
		final String password = "test";
		// sut.portUserInterface().getProvided(IUserAuthenticationRequest.class).requestLogin(session, username, password);

		mockGui.expect(ICAuthenticatorRequest.class, "requestLogin");

	}

}
