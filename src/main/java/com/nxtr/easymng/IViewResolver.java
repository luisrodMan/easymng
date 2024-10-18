package com.nxtr.easymng;

import com.nxtr.easymng.workspace.IWorkspaceItem;

public interface IViewResolver {

	boolean canHandle(IWorkspaceItem item);

	View getView(IWorkspaceItem item);

}
