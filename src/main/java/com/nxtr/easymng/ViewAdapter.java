package com.nxtr.easymng;

import java.util.UUID;

public abstract class ViewAdapter implements View {

	private String id;

	public ViewAdapter() {
		this(UUID.randomUUID().toString());
	}

	public ViewAdapter(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void restore(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttached(Bundle bundle) {

	}

	@Override
	public void save(Bundle bundle) {
		// TODO Auto-generated method stub

	}

}
