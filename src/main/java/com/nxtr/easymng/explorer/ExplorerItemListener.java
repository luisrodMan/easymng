package com.nxtr.easymng.explorer;

import java.util.List;

public interface ExplorerItemListener {
	void onItemsAdded(List<IExplorerItemUnit> path, int index, List<IExplorerItemUnit> units);

	void onItemsRemoved(List<IExplorerItemUnit> path, List<IExplorerItemUnit> units);

	void onExpand(List<IExplorerItemUnit> path);

	void onCollapse(List<IExplorerItemUnit> path);
}
