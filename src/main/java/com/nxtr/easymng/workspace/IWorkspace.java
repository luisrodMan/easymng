package com.nxtr.easymng.workspace;

import java.io.File;
import java.util.List;

public interface IWorkspace extends IWorkspaceItem {

	void importProject(IProject project);

	void importProject(File file);

	File getDirectory();

	List<IProject> getProjects();

}
