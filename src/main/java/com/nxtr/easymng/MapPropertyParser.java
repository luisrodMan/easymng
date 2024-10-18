package com.nxtr.easymng;

import java.util.Map;

public class MapPropertyParser extends PropertyParser {

	private Map<String, String> map;

	public MapPropertyParser(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public Map<String, String> getProperties() {
		return map;
	}

	@Override
	public int getPropertieCount() {
		return map.size();
	}

	@Override
	public void put(String name, String value) {
		map.put(name, value);
	}

	@Override
	public String getString(String name) {
		return map.get(name);
	}

}
