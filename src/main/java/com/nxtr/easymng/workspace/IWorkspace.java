package com.nxtr.easymng.workspace;

import java.io.File;

public interface IWorkspace extends IWorkspaceItem {

	void importProject(IProject project);

	void importProject(File file);
	
	File getDirectory();

}
