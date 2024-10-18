package com.nxtr.easymng.view;

public class DefaultViewDescriptor implements ViewDescriptor {

	private String path;

	public DefaultViewDescriptor(String string) {
		this.path = string.toUpperCase();
	}

	public String getPath() {
		return path;
	}

}
