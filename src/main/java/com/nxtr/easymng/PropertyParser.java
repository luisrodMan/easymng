package com.nxtr.easymng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class PropertyParser {

	public void put(String name, String[] data) {
		put(name, String.join("~.`~", data));
	}

	public void put(String name, Object[] data) {
		var datas = List.of(data).stream().map(Object::toString).toList();
		put(name, datas.toArray(new String[datas.size()]));
	}

	public String[] getArray(String name) {
		String value = getString(name);
		return value == null ? null : value.split("~.`~");
	}

	public abstract Map<String, String> getProperties();

	public abstract int getPropertieCount();

	public void put(String name, int value) {
		put(name, String.valueOf(value));
	}

	public void put(String name, float value) {
		put(name, String.valueOf(value));
	}

	public abstract void put(String name, String value);

	public abstract String getString(String name);

	public int getInt(String name, int def) {
		try {
			return Integer.parseInt(getString(name));
		} catch (Exception e) {
			return def;
		}
	}

	public float getFloat(String name, float def) {
		try {
			return Float.parseFloat(getString(name));
		} catch (Exception e) {
			return def;
		}
	}

	public static PropertyParser getParser(JsonElement object) {
		return getParser(object instanceof JsonObject ? (JsonObject) object : null);
	}

	public static PropertyParser getParser(JsonObject object) {
		return new JsonPropertyParser(object);
	}

	public static class JsonPropertyParser extends PropertyParser {

		private JsonObject object;

		public JsonPropertyParser(JsonObject object) {
			this.object = object;
		}

		@Override
		public Map<String, String> getProperties() {
			if (object == null)
				return new HashMap<>();
			else {
				Map<String, String> map = new HashMap<>();
				object.entrySet().forEach(e -> map.put(e.getKey(), e.getValue().getAsString()));
				return map;

			}
		}

		@Override
		public int getPropertieCount() {
			return object.keySet().size();
		}

		@Override
		public String getString(String name) {
			JsonElement e = object.get(name);
			return e == null ? null : e.getAsString();
		}

		@Override
		public void put(String name, String value) {
			object.addProperty(name, value);
		}

	}

}
