package com.nxtr.easymng.workspace;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.nxtr.easymng.DefaultItemPath;

public abstract class AbstractWorkspaceItem implements IWorkspaceItem {

	private transient List<WorkspaceItemListener> listeners;
	private transient List<IWorkspaceItem> items;
	private transient IWorkspaceItem parent;
	private String id, name;

	public AbstractWorkspaceItem(String id, String name) {
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

	protected void setName(String name) {
		this.name = name;
	}

	protected final void addItem(int index, AbstractWorkspaceItem item) {
		addItems(index, List.of(item));
	}

	protected void addItems(int index, List<AbstractWorkspaceItem> items) {
		if (this.items == null)
			this.items = new LinkedList<>();
		this.items.addAll(index, items);
		items.forEach(i -> i.parent = this);
		if (listeners != null) {
			var toadd = items.stream().map(s -> (IWorkspaceItem) s).toList();
			listeners.forEach(c -> c.onWorkspaceItemAdded(new DefaultItemPath(this), index, toadd));
		}
	}

	protected void removeItem(AbstractWorkspaceItem item) {
		if (items == null)
			throw new RuntimeException();
		items.remove(item);
		item.parent = null;
	}

	@Override
	public int getItemsCount() {
		return items == null ? 0 : items.size();
	}

	@Override
	public List<IWorkspaceItem> getItems() {
		return !hasItems() ? new ArrayList<>(0) : new ArrayList<>(items);
	}

	@Override
	public int getChildIndex(IWorkspaceItem workspaceItem) {
		if (!hasItems())
			throw new RuntimeException("invalid state");
		return items.indexOf(workspaceItem);
	}

	@Override
	public void addItemListener(WorkspaceItemListener listener) {
		if (listeners == null)
			listeners = new ArrayList<>();
		listeners.add(listener);
	}

	@Override
	public void removeWorkspaceItemListener(WorkspaceItemListener listener) {
		if (listeners == null)
			throw new RuntimeException();
		listeners.remove(listener);
		if (listeners.isEmpty())
			listeners = null;
	}

	@Override
	public void save() {

	}

}
