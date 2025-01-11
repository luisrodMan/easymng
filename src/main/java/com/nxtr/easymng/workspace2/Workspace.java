package com.nxtr.easymng.workspace2;

import java.io.File;
import java.util.List;

import com.nxtr.easymng.PropertyParser;

public interface Workspace extends Resource {

	File getDirectory();

	List<Project> getProjects();

	Project importProject(File file);

	PropertyParser getConfiguration();

	void load(WorkspaceDescriptor descriptor);

	void save();

}
