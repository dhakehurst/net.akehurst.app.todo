package net.akehurst.app.todo.computational.interfaceListStore;

import net.akehurst.application.framework.common.interfaceUser.UserSession;

public interface IListStoreRequest {

	void requestCreateNewList(UserSession session, ListStoreListSumary newList);

	void requestAvailableListsFor(UserSession session);

}
