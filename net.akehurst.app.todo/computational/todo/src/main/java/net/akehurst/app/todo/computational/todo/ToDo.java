/**
 * Copyright (C) 2016 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.akehurst.app.todo.computational.todo;

import java.util.HashSet;
import java.util.Set;

import net.akehurst.app.todo.computational.interfaceListStore.IListStoreNotification;
import net.akehurst.app.todo.computational.interfaceListStore.IListStoreRequest;
import net.akehurst.app.todo.computational.interfaceListStore.ListStoreListIdentity;
import net.akehurst.app.todo.computational.interfaceListStore.ListStoreListSumary;
import net.akehurst.app.todo.computational.interfaceListStore.ListStoreOwner;
import net.akehurst.app.todo.computational.interfaceUser.IUserHomeNotification;
import net.akehurst.app.todo.computational.interfaceUser.IUserHomeRequest;
import net.akehurst.app.todo.computational.interfaceUser.IUserWelcomeNotification;
import net.akehurst.app.todo.computational.interfaceUser.IUserWelcomeRequest;
import net.akehurst.app.todo.computational.interfaceUser.ToDoListIdentity;
import net.akehurst.app.todo.computational.interfaceUser.ToDoListSummary;
import net.akehurst.application.framework.common.IPort;
import net.akehurst.application.framework.common.annotations.instance.PortContract;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorNotification;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorRequest;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.realisation.AbstractComponent;

public class ToDo extends AbstractComponent
		implements IUserWelcomeRequest, IUserAuthenticationRequest, IUserHomeRequest, ICAuthenticatorNotification, IListStoreNotification {

	public ToDo(final String id) {
		super(id);
	}

	// --------- IUserWelcomeRequest ----------
	@Override
	public void requestSelectWelcome(final UserSession session) {
		this.portUserInterface().out(IUserWelcomeNotification.class).notifyWelcomeMessage(session, "Welcome, please login to access your ToDo lists.");
	}

	// --------- IUserAuthenticationRequest ---------
	@Override
	public void requestLogin(final UserSession session, final String username, final String password) {
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogin(session, username, password);
	}

	@Override
	public void requestLogout(final UserSession session) {
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogout(session);
	}

	// --------- IUserHomeRequest ---------
	@Override
	public void requestSelectHome(final UserSession session) {
		this.portStore().out(IListStoreRequest.class).requestAvailableListsFor(session);
	}

	@Override
	public void requestCreateNewList(final UserSession session, final ToDoListSummary value) {
		final ListStoreOwner owner = new ListStoreOwner(session.getUser().getName());
		final ListStoreListIdentity id = new ListStoreListIdentity(value.getId().getValue());
		final ListStoreListSumary newList = new ListStoreListSumary(owner, id, value.getDescription());
		this.portStore().out(IListStoreRequest.class).requestCreateNewList(session, newList);
	};

	@Override
	public void requestDeleteList(final UserSession session, final ToDoListIdentity listId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestViewList(final UserSession session, final ToDoListIdentity listId) {
		// TODO Auto-generated method stub

	}

	// --------- ICAuthenticatorNotification ----------
	@Override
	public void notifyAuthenticationSuccess(final UserSession session) {
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationSuccess(session);
	};

	@Override
	public void notifyAuthenticationFailure(final UserSession session, final String message) {
		final UserSession us = new UserSession(session.getId(), null);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationFailure(session, message);
	}

	@Override
	public void notifyAuthenticationCleared(final UserSession session) {
		final UserSession us = new UserSession(session.getId(), null);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationCleared(session);
	}

	// --------- IListStoreNotification ---------
	@Override
	public void notifyAvailableListsFor(final UserSession session, final Set<ListStoreListSumary> value) {
		final Set<ToDoListSummary> dummy = new HashSet<>();

		for (final ListStoreListSumary lst : value) {
			dummy.add(new ToDoListSummary(new ToDoListIdentity(lst.getId().getValue()), lst.getDescription()));
		}

		this.portUserInterface().out(IUserHomeNotification.class).notifyAvailableLists(session, dummy);

	};

	// --------- Ports ----------
	@PortInstance
	@PortContract(provides = IUserWelcomeRequest.class, requires = IUserWelcomeNotification.class)
	@PortContract(provides = IUserAuthenticationRequest.class, requires = IUserAuthenticationNotification.class)
	@PortContract(provides = IUserHomeRequest.class, requires = IUserHomeNotification.class)
	IPort portUserInterface;

	public IPort portUserInterface() {
		return this.portUserInterface;
	}

	@PortInstance
	@PortContract(provides = ICAuthenticatorNotification.class, requires = ICAuthenticatorRequest.class)
	IPort portAuth;

	public IPort portAuth() {
		return this.portAuth;
	}

	@PortInstance
	@PortContract(provides = IListStoreNotification.class, requires = IListStoreRequest.class)
	IPort portStore;

	public IPort portStore() {
		return this.portStore;
	}
}
