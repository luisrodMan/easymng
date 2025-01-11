package com.nxtr.easymng.workspace2;

import java.io.File;

import com.nxtr.easymng.PropertyParser;

public interface Project extends Resource {

	String getVersion();

	File getDirectory();

	String getType();

	PropertyParser getConfiguration();

	void load(ProjectDescriptor descriptor);

}
