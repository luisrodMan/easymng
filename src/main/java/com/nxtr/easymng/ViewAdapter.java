package com.nxtr.easymng;

import java.util.UUID;

public abstract class ViewAdapter implements View {

	private String id;
	private String title;

	public ViewAdapter(String title) {
		this(UUID.randomUUID().toString(), title);
	}

	public ViewAdapter(String id, String title) {
		this.id = id;
		setTitle(title);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void restore(Bundle bundle) {
		setTitle(bundle.getString("viewTitle"));
	}

	protected void setTitle(String string) {
		this.title = string;
	}

	@Override
	public void onAttached(Bundle bundle) {

	}

	@Override
	public void save(Bundle bundle) {
		bundle.put("viewTitle", getTitle());
	}

}
