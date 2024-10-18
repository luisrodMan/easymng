package com.nxtr.easymng.explorer;

import java.util.List;

import com.nxtr.easymng.workspace.IWorkspaceItem;

public abstract class ExplorerItemUnit extends IExplorerItemUnit {

	public ExplorerItemUnit(IWorkspaceItem model) {
		super(model);
	}

	public void addChildren(int index, List<IExplorerItemUnit> units) {
		super.addChildren(index, units);
	}

}
