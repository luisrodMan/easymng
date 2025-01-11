package com.nxtr.easymng.workspace;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class ReferenceWorkspace extends AbstractWorkspace {

	public ReferenceWorkspace(String name) {
		super(UUID.randomUUID().toString(), name);
	}

	@Override
	public void importProject(IProject project) {

	}

	@Override
	public void importProject(File file) {

	}

	@Override
	public File getDirectory() {
		return null;
	}

	@Override
	public List<IProject> getProjects() {
		return null;
	}

}
