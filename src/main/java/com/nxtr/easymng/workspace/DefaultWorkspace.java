package com.nxtr.easymng.workspace;

import java.io.File;
import java.util.List;

public abstract class DefaultWorkspace extends DefaultWorkspaceItem implements IWorkspace {

	private File path;

	public DefaultWorkspace(String id, File path, List<IProject> projects) {
		super(id, path.getName());
		this.path = path;
	}

	@Override
	public void importProject(IProject project) {

	}

	@Override
	public void importProject(File file) {

	}

	@Override
	public File getDirectory() {
		return path;
	}

	@Override
	public List<IProject> getProjects() {
		return getItems().stream().map(IProject.class::cast).toList();
	}

}
