package net.akehurst.app.todo.engineering.user2Gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.akehurst.app.todo.computational.interfaceUser.IUserHomeNotification;
import net.akehurst.app.todo.computational.interfaceUser.IUserHomeRequest;
import net.akehurst.app.todo.computational.interfaceUser.ToDoListIdentity;
import net.akehurst.app.todo.computational.interfaceUser.ToDoListSummary;
import net.akehurst.application.framework.common.annotations.declaration.ExternalConnection;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.realisation.AbstractIdentifiableObject;
import net.akehurst.application.framework.technology.interfaceGui.GuiEvent;
import net.akehurst.application.framework.technology.interfaceGui.IGuiHandler;
import net.akehurst.application.framework.technology.interfaceGui.IGuiScene;
import net.akehurst.application.framework.technology.interfaceGui.IGuiSceneHandler;
import net.akehurst.application.framework.technology.interfaceGui.SceneIdentity;
import net.akehurst.application.framework.technology.interfaceGui.StageIdentity;
import net.akehurst.application.framework.technology.interfaceGui.data.table.AbstractGuiTableData;
import net.akehurst.application.framework.technology.interfaceGui.data.table.IGuiTableData;

public class SceneHandlerHome extends AbstractIdentifiableObject implements IUserHomeNotification, IGuiSceneHandler {

	public SceneHandlerHome(final String afId) {
		super(afId);
	}

	@ExternalConnection
	public IUserHomeRequest userHomeRequest;

	@ExternalConnection
	public IUserAuthenticationRequest userAuthenticationRequest;

	private IHomeScene scene;

	@Override
	public IGuiScene createScene(final IGuiHandler gui, final StageIdentity stageId, final URL content) {
		this.scene = gui.createScene(stageId, SceneHandlerCommon.sceneIdHome, IHomeScene.class, this, content);
		return this.scene;
	}

	@Override
	public void loaded(final IGuiHandler gui, final IGuiScene guiScene, final GuiEvent event) {
		final UserSession session = event.getSession();
		final SceneIdentity currentSceneId = event.getSignature().getSceneId();

		this.scene.getUsername().setText(session, session.getUser().getName());
		this.scene.getActionSignOut().onEvent(session, "click", (e) -> {
			this.userAuthenticationRequest.requestLogout(session);
		});

		this.scene.getActionCreateNewList().onEvent(session, "click", (e) -> {
			this.userHomeRequest.requestCreateNewList(session, new ToDoListSummary(new ToDoListIdentity("newList"), "A new List"));
		});

		this.userHomeRequest.requestSelectHome(session);

	}

	@Override
	public void notifyAvailableLists(final UserSession session, final Set<ToDoListSummary> available) {
		final List<ToDoListSummary> availablList = new ArrayList<>(available);
		final IGuiTableData<String, Integer> data = new AbstractGuiTableData<String, Integer>() {

			@Override
			public int getNumberOfRows() {
				return availablList.size();
			}

			@Override
			public Map<String, Object> getRowData(final int index) {
				final ToDoListSummary p = availablList.get(index);
				final Map<String, Object> r = new HashMap<>();
				r.put("identity", p.getId().getValue());
				r.put("description", p.getDescription());
				return r;
			}
		};

		this.scene.getTableAvailableLists().setData(session, data);
	}

}
