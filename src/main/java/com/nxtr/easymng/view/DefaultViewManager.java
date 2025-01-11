package com.nxtr.easymng.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ngeneration.furthergui.FComponent;
import com.nxtr.easymng.Application;
import com.nxtr.easymng.PropertyParser;
import com.nxtr.easymng.View;
import com.nxtr.easymng.ViewResolverManager;

public class DefaultViewManager implements ViewManager {

	private ViewResolverManager viewResolverManager = new ViewResolverManager();
	private ViewSplit mainSplit = new ViewSplit();

	private Map<String, View> views = new HashMap<>();
	private List<String> viewsOrder = new LinkedList<>();
	private List<ViewManagerListener> listeners = new LinkedList<>();
	private View focusedView;
	private Application application;

	public DefaultViewManager(Application application) {
		this.application = application;
	}

	private void updateViewsOrder() {
		View view = getActiveView();
		if (view != null) {
			viewsOrder.remove(view.getId());
			viewsOrder.add(view.getId());
		} else
			viewsOrder.clear();
	}

	public String getPath(View v) {
		return mainSplit.getPath(v);
	}

	@Override
	public void addView(View view, ViewDescriptor descriptor1) {
		if (containsView(view.getId()))
			setActiveView(view.getId());
		else {
			DefaultViewDescriptor descriptor = descriptor1 instanceof DefaultViewDescriptor
					? (DefaultViewDescriptor) descriptor1
					: new DefaultViewDescriptor("C");
			String[] path = descriptor.getPath().split("(?!^)");
			System.out.println("length: " + path.length + " " + String.join("-", path));
			mainSplit.add(path, 0, view);

			viewsOrder.add(view.getId());
			updateViewsOrder();
			views.put(view.getId(), view);
		}

	}

	@Override
	public void closeView(String viewId) {
		View view = getViewById(viewId);
		if (view != null) {
			views.remove(viewId);
			viewsOrder.remove(viewId);
			if (!viewsOrder.isEmpty()) {
				setActiveView(viewsOrder.get(viewsOrder.size() - 1));
			}
			mainSplit.removeView(view);
		}
	}

	@Override
	public View getActiveView() {
//		Object i = tabs.getSelectedComponent();
//		if (i != null) {
//			for (View view : views.values()) {
//				if (view.getComponent() == i)
//					return view;
//			}
//		}
		return null;
	}

	@Override
	public View getViewById(String id) {
		return views.get(id);
	}

	@Override
	public List<View> getViews() {
		return new LinkedList<>(views.values());
	}

	@Override
	public boolean setActiveView(String viewId) {
//		View view = views.get(viewId);
//		if (view != null) {
//			tabs.setSelectedIndex(getViewIndex(tabs, view));
//			return true;
//		} else
		return false;
	}

	public List<String> getActiveViewOrder() {
		return new LinkedList<>(viewsOrder);
	}

	@Override
	public boolean containsView(String view) {
		return views.containsKey(view);
	}

	@Override
	public ViewResolverManager getViewResolverManager() {
		return viewResolverManager;
	}

	public FComponent getComponent() {
		return mainSplit;
	}

	@Override
	public List<View> getActiveViews() {
		return mainSplit.getActiveViews();
	}

	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public void addViewManagerListener(ViewManagerListener viewManagerListener) {
		listeners.add(viewManagerListener);
	}

	@Override
	public void removeViewManagerListener(ViewManagerListener viewManagerListener) {
		listeners.remove(viewManagerListener);
	}

	@Override
	public View getFocusedView() {
		return focusedView;
	}

	@Override
	public void save(Map<String, Object> props) {
		Map<String, Object> p = new HashMap<>();
		mainSplit.save(p);
		props.put("views", p);
	}

	@Override
	public void restore(Map<String, Object> map) {
		if (map != null)
			mainSplit.restore(this, map.getProperties("views"));
	}

}
