package com.nxtr.easymng.view;

import java.util.List;

import com.nxtr.easymng.View;
import com.nxtr.easymng.ViewResolverManager;
import com.nxtr.easymng.workspace.IWorkspaceItem;

public interface ViewManager {

	ViewResolverManager getViewResolverManager();

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

	void closeView(String viewId);

	View getActiveView();

	View getViewById(String id);

	List<View> getViews();

	boolean setActiveView(String viewId);

	default boolean containsView(String view) {
		return getViewById(view) != null;
	}

	List<View> getActiveViews();

}
