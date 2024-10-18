package com.nxtr.easymng.explorer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ngeneration.furthergui.TreeNode;
import com.nxtr.easymng.workspace.IWorkspaceItem;

public abstract class IExplorerItemUnit implements TreeNode {

	private IExplorerItemUnit parent;
	private List<ExplorerItemListener> listeners;
	private List<IExplorerItemUnit> children;
	private IWorkspaceItem model;

	public IExplorerItemUnit(IWorkspaceItem model) {
		this.model = model;
	}

	public IWorkspaceItem getModel() {
		return model;
	}

	public String getName() {
		return model.getName();
	}

	public void addListener(ExplorerItemListener listener) {
		if (listeners == null)
			listeners = new LinkedList<>();
		listeners.add(listener);
	}

	public void removeListener(ExplorerItemListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty())
				listeners = null;
		}
	}

	protected void addChildren(int index, List<IExplorerItemUnit> units) {
		if (children == null)
			children = new LinkedList<IExplorerItemUnit>();
		children.addAll(index, units);
		List<IExplorerItemUnit> path = new LinkedList<IExplorerItemUnit>();
		fireAddListener(path, index, units);
	}

	protected final void addChildren(int index, IExplorerItemUnit list) {
		addChildren(index, List.of(list));
	}

	private void fireAddListener(List<IExplorerItemUnit> path, int index, List<IExplorerItemUnit> units) {
		path.add(0, this);
		if (listeners != null)
			listeners.forEach(l -> l.onItemsAdded(path, index, units));
		if (parent != null)
			parent.fireAddListener(path, index, units);
	}

	protected void removeChildren(List<IExplorerItemUnit> units) {
		if (children != null) {
			children.removeAll(units);
			List<IExplorerItemUnit> path = new LinkedList<>();
			fireRemoveListener(path, units);
		}
	}

	private void fireRemoveListener(List<IExplorerItemUnit> path, List<IExplorerItemUnit> units) {
		path.add(0, this);
		if (listeners != null)
			listeners.forEach(l -> l.onItemsRemoved(path, units));
		if (parent != null)
			parent.fireRemoveListener(path, units);
	}

	public void expand() {
		List<IExplorerItemUnit> path = new LinkedList<IExplorerItemUnit>();
		expand(path);
	}

	private void expand(List<IExplorerItemUnit> path) {
		path.add(0, this);
		if (listeners != null)
			listeners.forEach(l -> l.onExpand(path));
		if (parent != null)
			parent.expand(path);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

	public int getChildCount() {
		return children == null ? 0 : children.size();
	}

	@Override
	public TreeNode getChildAt(int i) {
		return children.get(i);
	}

	public List<TreeNode> getChildren() {
		return children == null ? new ArrayList<>(0) : new ArrayList<>(children);
	}

	@Override
	public boolean allowsChildren() {
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}

	public abstract String getTags();

}
