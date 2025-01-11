package com.nxtr.easymng;

import java.util.HashMap;
import java.util.Map;

public class Bundle extends MapPropertyParser {

	private volatile Application application;

	public Bundle(Application application) {
		this(application, new HashMap<>());
	}

	public Bundle(Application application, Map<String, Object> map) {
		super(map);
		this.application = application;
	}

	public Application getApplication() {
		return application;
	}

}
