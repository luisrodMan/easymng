package com.nxtr.easymng;

import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nxtr.easymng.workspace2.PropertiesParserDeserializer;

public class DefaultApplicationData implements ApplicationData {

	private String version;
	private String activeWorkspace;
	private List<String> recents;
	private PropertyParser configuration;

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getActiveWorkspace() {
		return activeWorkspace;
	}

	@Override
	public List<String> getRecents() {
		return recents;
	}

	@Override
	public PropertyParser getConfiguration() {
		return configuration;
	}

	public static DefaultApplicationData load(String json) {
		try (FileReader reader = new FileReader(json)) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(PropertyParser.class, new PropertiesParserDeserializer());
			Gson gson = gsonBuilder.create();
			return gson.fromJson(reader, DefaultApplicationData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
