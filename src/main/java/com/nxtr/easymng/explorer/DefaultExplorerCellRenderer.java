package com.nxtr.easymng.explorer;

import com.ngeneration.furthergui.DefaultTreeCellRenderer;
import com.ngeneration.furthergui.FComponent;

public class DefaultExplorerCellRenderer extends DefaultTreeCellRenderer implements ExplorerCellRenderer {

	@Override
	public FComponent getExplorerCellRendererComponent(ExplorerView explorer, ExplorerItemUnit unit, boolean selected,
			boolean expanded, boolean leaf, int row, boolean hasFocus) {
		return getTreeCellRendererComponent(explorer.getTree(), unit.getName(), selected, expanded, leaf, row,
				hasFocus);
	}

}
