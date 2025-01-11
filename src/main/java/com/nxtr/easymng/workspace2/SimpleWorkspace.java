package com.nxtr.easymng.workspace2;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.nxtr.easymng.PropertyParser;

public class SimpleWorkspace implements Workspace {

	private List<Project> projects = new LinkedList<>();
	private File directory;
	private PropertyParser configuration;

	public SimpleWorkspace() {
	}

	public SimpleWorkspace(WorkspaceDescriptor descriptor) {
		load(descriptor);
	}

	public SimpleWorkspace(File dir, List<Project> projects) {
		this.directory = dir;
		this.projects = new LinkedList<>(projects);
	}

	@Override
	public File getDirectory() {
		return directory;
	}

	@Override
	public List<Project> getProjects() {
		return new LinkedList<>(projects);
	}

	@Override
	public Project importProject(File file) {
		throw new RuntimeException("Not supported");
	}

	@Override
	public void load(WorkspaceDescriptor descriptor) {
		projects.addAll(descriptor.getProjects());
		this.configuration = descriptor.getConfiguration();
		this.directory = new File(descriptor.getDirectory());
	}

	@Override
	public Workspace getRoot() {
		return this;
	}

	@Override
	public Item getParent() {
		return null;
	}

	@Override
	public PropertyParser getConfiguration() {
		return configuration;
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void open() {

	}

	@Override
	public void close() {
	}

	@Override
	public void save() {

	}

}
