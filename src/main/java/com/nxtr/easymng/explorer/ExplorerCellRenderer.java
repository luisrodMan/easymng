package com.nxtr.easymng.explorer;

import com.ngeneration.furthergui.FComponent;

public interface ExplorerCellRenderer {

	public FComponent getExplorerCellRendererComponent(ExplorerView explorer, ExplorerItemUnit unit, boolean selected,
			boolean expanded, boolean leaf, int row, boolean hasFocus);

}
