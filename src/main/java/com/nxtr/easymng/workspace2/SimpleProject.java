package com.nxtr.easymng.workspace2;

import java.io.File;

import com.nxtr.easymng.PropertyParser;

public class SimpleProject implements Project {

	private File file;
	private String version;
	private String type;
	private PropertyParser configuration;
	private String name;

	public SimpleProject() {
	}

	public SimpleProject(ProjectDescriptor descriptor) {
		load(descriptor);
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public File getDirectory() {
		return file.getParentFile();
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public PropertyParser getConfiguration() {
		return configuration;
	}

	@Override
	public void load(ProjectDescriptor descriptor) {
		this.file = new File(descriptor.getFile());
		this.name = descriptor.getName();
		this.configuration = descriptor.getConfiguration();
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public Workspace getRoot() {
		return null;
	}

	@Override
	public Item getParent() {
		return null;
	}

}
