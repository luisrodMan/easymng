package com.nxtr.easymng.view;

import java.util.LinkedList;
import java.util.List;

import com.ngeneration.furthergui.FComponent;
import com.ngeneration.furthergui.FTabbedPane;
import com.nxtr.easymng.View;

public class DefaultViewContainer implements ViewContainer {

	private FTabbedPane tabs = new FTabbedPane();
	private List<View> views = new LinkedList<>();

	public void addView(View view) {
		views.add(view);
		tabs.addTab(view.getTitle(), view.getComponent());
	}

	@Override
	public List<View> getViews() {
		return new LinkedList<>(views);
	}

	@Override
	public FComponent getComponent() {
		return tabs;
	}

	private int getViewIndex(FTabbedPane tabs2, View view) {
		for (int i = 0; i < tabs.getTabCount(); i++)
			if (tabs.getComponentAt(i) == view.getComponent())
				return i;
		return -1;
	}

	@Override
	public boolean contains(View view) {
		return views.contains(view);
	}

	public View getActiveView() {
		int idx = tabs.getSelectedIndex();
		return idx < 0 ? null : views.get(idx);
	}

}
