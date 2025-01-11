package com.nxtr.easymng.view;

import java.util.List;
import java.util.Map;

import com.nxtr.easymng.Application;
import com.nxtr.easymng.View;
import com.nxtr.easymng.ViewResolverManager;
import com.nxtr.easymng.workspace.IWorkspaceItem;

public interface ViewManager {

	Application getApplication();

	ViewResolverManager getViewResolverManager();

	void addViewManagerListener(ViewManagerListener viewManagerListener);

	void removeViewManagerListener(ViewManagerListener viewManagerListener);

	default void addViewAndSetActive(View view) {
		addView(view);
		setActiveView(view.getId());
	}

	default void addViewAndSetActive(IWorkspaceItem model) {
		var view = getViewById(model.getId());
		if (view == null)
			view = getViewResolverManager().resolve(model);
		if (view != null)
			addViewAndSetActive(view);
	}

	default void addView(View view) {
		addView(view, null);
	}

	void addView(View view, ViewDescriptor descriptor);

	View getViewById(String id);

	List<View> getViews();

	View getActiveView();

	List<View> getActiveViews();

	View getFocusedView();

	void closeView(String viewId);

	boolean setActiveView(String viewId);

	default boolean containsView(String view) {
		return getViewById(view) != null;
	}

	void save(Map<String, Object> props);

	void restore(Map<String, Object> props);

}
