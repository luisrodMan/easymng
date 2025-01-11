package com.nxtr.easymng;

import java.util.HashMap;
import java.util.Map;

public class MapPropertyParser extends PropertyParser {

	private Map<String, Object> map;

	public MapPropertyParser(Map<String, Object> map) {
		this.map = map;
	}

	public MapPropertyParser() {
		this(new HashMap<>());
	}

	@Override
	public Map<String, Object> getProperties() {
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
		var data = map.get(name);
		return data == null ? null : data.toString();
	}

}
