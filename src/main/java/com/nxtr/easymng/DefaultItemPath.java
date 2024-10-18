package com.nxtr.easymng;

import com.nxtr.easymng.workspace.IWorkspaceItem;
import com.nxtr.easymng.workspace.IWorkspaceItemPath;

public class DefaultItemPath implements IWorkspaceItemPath {

	private IWorkspaceItem endItem;

	public DefaultItemPath(IWorkspaceItem endItem) {
		this.endItem = endItem;
	}

	@Override
	public IWorkspaceItem firstItem() {
		return endItem.getRoot();
	}

	@Override
	public IWorkspaceItem lastItem() {
		return endItem;
	}

}
