package com.nxtr.easymng.workspace;

import java.util.List;

public interface IWorkspaceItem {

	String getId();

	String getName();

	IWorkspaceItem getParent();

	default IWorkspaceItem getRoot() {
		return getPath()[0];
	}

	default IWorkspace getWorkspace() {
		var root = getRoot();
		return root instanceof IWorkspace ? (IWorkspace) root : null;
	}

	default IWorkspaceItem getItemById(String id) {
		if (!hasItems())
			return null;
		else
			return getItems().stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
	}

	default IWorkspaceItem getItemByPath(String[] path) {
		if (!this.getId().equals(path[0]))
			return null;
		var item = this;
		int index = 1;
		while (item != null && index < path.length)
			item = item.getItemById(path[index++]);
		return item;
	}

	default IWorkspaceItem[] getPath() {
		IWorkspaceItem root = this;
		int size = 1;
		while (root.getParent() != null) {
			size++;
			root = root.getParent();
		}
		var path = new IWorkspaceItem[size];
		root = this;
		while (root != null) {
			path[--size] = root;
			root = root.getParent();
		}
		return path;
	}

	int getItemsCount();

	default boolean hasItems() {
		return getItemsCount() > 0;
	}

	int getChildIndex(IWorkspaceItem workspaceItem);

	List<IWorkspaceItem> getItems();

	default List<IWorkspaceItem> getItemsByName(String name) {
		return getItems().stream().filter(i -> i.getName().equals(name)).toList();
	}

	void addItemListener(WorkspaceItemListener listener);

	void removeWorkspaceItemListener(WorkspaceItemListener listener);

	void save();

}