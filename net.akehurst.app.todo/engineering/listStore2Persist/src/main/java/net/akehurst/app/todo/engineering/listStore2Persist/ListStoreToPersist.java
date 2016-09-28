package net.akehurst.app.todo.engineering.listStore2Persist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.akehurst.app.todo.computational.interfaceListStore.IListStoreNotification;
import net.akehurst.app.todo.computational.interfaceListStore.IListStoreRequest;
import net.akehurst.app.todo.computational.interfaceListStore.ListStoreListIdentity;
import net.akehurst.app.todo.computational.interfaceListStore.ListStoreListSumary;
import net.akehurst.app.todo.computational.interfaceListStore.ListStoreOwner;
import net.akehurst.application.framework.common.IPort;
import net.akehurst.application.framework.common.annotations.instance.ConfiguredValue;
import net.akehurst.application.framework.common.annotations.instance.PortContract;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistenceTransaction;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistentStore;
import net.akehurst.application.framework.technology.interfacePersistence.PersistentItemQuery;
import net.akehurst.application.framework.technology.interfacePersistence.PersistentStoreException;

public class ListStoreToPersist extends AbstractComponent implements IListStoreRequest {

	public ListStoreToPersist(final String id) {
		super(id);
	}

	@ConfiguredValue(defaultValue = "org.apache.derby.jdbc.EmbeddedDriver")
	String connectionDriver;

	@ConfiguredValue(defaultValue = "jdbc:derby:db/ToDo;create=true")
	String connectionUrl;

	@Override
	public void afRun() {
		final Map<String, Object> props = new HashMap<>();
		props.put("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		props.put("javax.jdo.option.ConnectionDriverName", this.connectionDriver);
		props.put("javax.jdo.option.ConnectionURL", this.connectionUrl);
		props.put("datanucleus.schema.autoCreateAll", "true");
		this.portPersist().out(IPersistentStore.class).connect(props);

		super.afRun();
	}

	// --------- IListStoreRequest ---------
	@Override
	public void requestCreateNewList(final UserSession session, final ListStoreListSumary newList) {
		final PersistentItemQuery id = new PersistentItemQuery("");
		final JdoListSummary jdoList = new JdoListSummary();
		jdoList.owner = newList.getOwner().getValue();
		jdoList.id = newList.getId().getValue();
		try {
			final IPersistenceTransaction transaction = this.portPersist().out(IPersistentStore.class).startTransaction();
			this.portPersist().out(IPersistentStore.class).store(transaction, id, jdoList, JdoListSummary.class);
			this.portPersist().out(IPersistentStore.class).commitTransaction(transaction);

			// ? notify

		} catch (final PersistentStoreException e) {
			e.printStackTrace();
		}
	};

	@Override
	public void requestAvailableListsFor(final UserSession session) {
		try {
			final IPersistenceTransaction transaction = this.portPersist().out(IPersistentStore.class).startTransaction();

			final Set<JdoListSummary> jdoLists = this.portPersist().out(IPersistentStore.class).retrieveAll(transaction, JdoListSummary.class);
			// final Set<ListStoreListSumary> availableLists = jdoLists.stream().filter((l) -> {
			// return this.authorisation.hasAuthorisation(session.getUser(), new AuthorisationPermision("Project.view"), new AuthorisationTarget(l.id));
			// }).map((i) -> {
			// final ListStoreOwner owner = new ListStoreOwner(i.owner);
			// final ListStoreListIdentity id = new ListStoreListIdentity(i.id);
			// final String description = i.description;
			// return new ListStoreListSumary(owner, id, description);
			// }).collect(Collectors.toSet());

			final Set<ListStoreListSumary> availableLists = new HashSet<>();
			for (final JdoListSummary jl : jdoLists) {
				final ListStoreOwner owner = new ListStoreOwner(jl.getOwner());
				final ListStoreListIdentity id = new ListStoreListIdentity(jl.getId());
				final String description = jl.getDescription();
				final ListStoreListSumary l = new ListStoreListSumary(owner, id, description);
				availableLists.add(l);
			}

			this.portPersist().out(IPersistentStore.class).commitTransaction(transaction);

			this.portStore().out(IListStoreNotification.class).notifyAvailableListsFor(session, availableLists);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	};

	// ---------- Ports ---------

	@PortInstance
	@PortContract(provides = IListStoreRequest.class, requires = IListStoreNotification.class)
	IPort portStore;

	public IPort portStore() {
		return this.portStore;
	}

	@PortInstance
	@PortContract(requires = IPersistentStore.class)
	IPort portPersist;

	public IPort portPersist() {
		return this.portPersist;
	}
}
