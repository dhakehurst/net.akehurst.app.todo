package net.akehurst.app.todo.computational.interfaceListStore;

import java.util.Set;

import net.akehurst.application.framework.common.interfaceUser.UserSession;

public interface IListStoreNotification {

	void notifyAvailableListsFor(UserSession session, Set<ListStoreListSumary> value);

}
