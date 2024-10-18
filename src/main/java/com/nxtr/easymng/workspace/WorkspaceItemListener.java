package com.nxtr.easymng.workspace;

import java.util.List;

public interface WorkspaceItemListener {

	void onWorkspaceItemAdded(IWorkspaceItemPath path, int index, List<IWorkspaceItem> workspaceItem);

	void onWorkspaceItemRemoved(IWorkspaceItemPath path, List<IWorkspaceItem> workspaceItem);

}
