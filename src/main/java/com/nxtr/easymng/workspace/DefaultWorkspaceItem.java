package com.nxtr.easymng.workspace;

import java.util.LinkedList;
import java.util.List;

public class DefaultWorkspaceItem implements IWorkspaceItem {

	private String id;
	private String name;
	private List<IWorkspaceItem> items = new LinkedList<>();
	private List<WorkspaceItemListener> listeners = new LinkedList<>();
	private IWorkspaceItem parent;

	public DefaultWorkspaceItem(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() { 
		return name;
	}

	@Override
	public IWorkspaceItem getParent() {
		return parent;
	}

	@Override
	public int getItemsCount() {
		return items.size();
	}

	@Override
	public int getChildIndex(IWorkspaceItem workspaceItem) {
		throw new RuntimeException();
	}

	@Override
	public List<IWorkspaceItem> getItems() {
		return new LinkedList<>(items);
	}

	@Override
	public void addItemListener(WorkspaceItemListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeWorkspaceItemListener(WorkspaceItemListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
