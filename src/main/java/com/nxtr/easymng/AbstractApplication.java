package com.nxtr.easymng;

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
import com.nxtr.easymng.workspace.IWorkspace;

public abstract class AbstractApplication implements Application {

	private Map<String, Object> properties = new HashMap<>();
	private Map<String, Action> actions;

	private KeyContainer keyContainer;
	private Set<ApplicationListener> listeners = new LinkedHashSet<>();
	private IWorkspace workspace;
	private ViewManager viewManager;

	public AbstractApplication() {
		Application.applicationHolder.application = this;
		viewManager = installViewManager();
		actions = loadActions();
		keyContainer = loadKeys();

//		tabs.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent event) {
//				updateViewsOrder();
//			}
//		});
	}

	protected ViewManager installViewManager() {
		return new DefaultViewManager();
	}

	public void addApplicationListener(ApplicationListener aplicationListenerAdapter) {
		listeners.add(aplicationListenerAdapter);
	}

	public void removeApplicationListener(ApplicationListener aplicationListenerAdapter) {
		listeners.remove(aplicationListenerAdapter);
	}

	@Override
	public IWorkspace getWorkspace() {
		return workspace;
	}

	@Override
	public void setActiveWorkspace(com.nxtr.easymng.workspace.WorkspaceInfo info) {
		workspace = loadWorkspace(info);
		listeners.forEach(l -> l.onWorkspaceChanged(this, info));
	}

	protected abstract IWorkspace loadWorkspace(com.nxtr.easymng.workspace.WorkspaceInfo info);

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
