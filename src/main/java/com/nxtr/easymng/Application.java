package com.nxtr.easymng;

import java.util.List;
import java.util.Map;

import com.ngeneration.furthergui.FComponent;
import com.ngeneration.furthergui.event.Action;
import com.nxtr.easymng.view.ViewManager;
import com.nxtr.easymng.workspace.IWorkspace;
import com.nxtr.easymng.workspace2.Workspace;

public interface Application {

	static final ApplicationHolder applicationHolder = new ApplicationHolder();

	static class ApplicationHolder {
		Application application = null;
	}

	static String LAST_DIRECTORY = "last_used_directory";

	static Application getInstance() {
		return applicationHolder.application;
	}

	List<String> getRecentWorspaces();

	PropertyParser getConfiguration();

	void addApplicationListener(ApplicationListener listener);

	void removeApplicationListener(ApplicationListener listener);

	void setWorkspace(Workspace workspace);

	Workspace getWorkspace();

	Object getProperty(String property);

	void setProperty(String property, Object value);

	Map<String, Object> getProperties();

	Map<String, Action> getActions();

	FComponent getControl();

	ViewManager getViewManager();

}
