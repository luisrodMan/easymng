package com.nxtr.easymng;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ngeneration.furthergui.event.Action;
import com.nxtr.easymng.keys.Key;
import com.nxtr.easymng.keys.KeyContainer;
import com.nxtr.easymng.view.DefaultViewManager;
import com.nxtr.easymng.view.ViewManager;
import com.nxtr.easymng.workspace2.SimpleWorkspace;
import com.nxtr.easymng.workspace2.Workspace;
import com.nxtr.easymng.workspace2.WorkspaceDescriptor;

public abstract class AbstractApplication implements Application {

	private Map<String, Object> properties = new HashMap<>();
	private Map<String, Action> actions;

	private KeyContainer keyContainer;
	private Set<ApplicationListener> listeners = new LinkedHashSet<>();
	private Workspace workspace;
	private ViewManager viewManager;

	private ApplicationData applicationData;

	public AbstractApplication() {
		this("settings.json");
	}

	public AbstractApplication(String configuration) {
		Application.applicationHolder.application = this;
		// load configuration
		applicationData = loadConfiguration(configuration);
		if (applicationData.getActiveWorkspace() != null) {
			if (new File(applicationData.getActiveWorkspace()).exists()) {
				setWorkspace(loadWorkspace(applicationData.getActiveWorkspace()));
			} else {
				applicationData.getRecents().remove(applicationData.getActiveWorkspace());
			}
		}
		viewManager = installViewManager();
		actions = loadActions();
		keyContainer = loadKeys();
	}

	protected void onClossingApplication() {
//		applicationData.
		PropertyParser props = new MapPropertyParser();
		getViewManager().save(props.getProperties());
		this.workspace.getConfiguration().put("viewMngState", props);
		// save workspace
		this.workspace.save();
		WorkspaceDescriptor descriptor = new WorkspaceDescriptor();
		descriptor.setConfiguration(this.workspace.getConfiguration());
		descriptor.setProjects(this.workspace.getProjects());
		try {
			descriptor.save(this.workspace.getDirectory());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Workspace loadWorkspace(String workspacePath) {
		Workspace workspace = null;
		try {
			WorkspaceDescriptor w = WorkspaceDescriptor.load(new File(workspacePath, "workspace"));
			if (w.getType() == null)
				workspace = new SimpleWorkspace(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workspace;
	}

	protected ApplicationData loadConfiguration(String configuration) {
		ApplicationData data = DefaultApplicationData.load(configuration);
		return data != null ? data : new DefaultApplicationData();
	}

	@Override
	public List<String> getRecentWorspaces() {
		return applicationData.getRecents();
	}

	@Override
	public PropertyParser getConfiguration() {
		return applicationData.getConfiguration();
	}

	protected ViewManager installViewManager() {
		return new DefaultViewManager(this);
	}

	public void addApplicationListener(ApplicationListener aplicationListenerAdapter) {
		listeners.add(aplicationListenerAdapter);
	}

	public void removeApplicationListener(ApplicationListener aplicationListenerAdapter) {
		listeners.remove(aplicationListenerAdapter);
	}

	@Override
	public Workspace getWorkspace() {
		return workspace;
	}

	@Override
	public void setWorkspace(Workspace info) {
		var old = this.workspace;
		if (old != null && old.isOpen())
			old.close();
		this.workspace = info;
		if (this.workspace != null && !this.workspace.isOpen()) {
			this.workspace.open();
			// views
			getViewManager().restore(this.workspace.getConfiguration().getProperties("viewMngState").getProperties());
		}
		if (old != null || this.workspace != null)
			listeners.forEach(l -> l.onWorkspaceChanged(this, old));
	}

	protected abstract Map<String, Action> loadActions();

	protected abstract KeyContainer loadKeys();

	@Override
	public Object getProperty(String property) {
		return properties.get(property);
	}

	@Override
	public void setProperty(String property, Object value) {
		properties.put(property, value);
	}

	@Override
	public Map<String, Object> getProperties() {
		return new HashMap<>(properties);
	}

	public List<Key> getKeys() {
		return keyContainer.getKeys();
	}

	public Map<String, Action> getActions() {
		return actions;
	}

	@Override
	public ViewManager getViewManager() {
		return viewManager;
	}

}
