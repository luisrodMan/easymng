package com.nxtr.easymng.workspace2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nxtr.easymng.PropertyParser;

import lombok.Data;

@Data
public class WorkspaceDescriptor {

	private String version;
	private String type;
	private String name;
	private volatile String directory;
	private List<Project> projects;
	private PropertyParser configuration;

	public static WorkspaceDescriptor load(File file) throws FileNotFoundException, IOException {
		try (FileReader reader = new FileReader(file)) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(PropertyParser.class, new PropertiesParserDeserializer());
			gsonBuilder.registerTypeAdapter(PropertyParser.class, new ProjectDeserializer());
			Gson gson = gsonBuilder.create();
			WorkspaceDescriptor w = gson.fromJson(reader, WorkspaceDescriptor.class);
			w.setDirectory(file.getParent());
			return w;
		}
	}

	public void save(File file) throws IOException {
		Gson gson = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectAdapter())
				.registerTypeAdapter(PropertyParser.class, new PropertyParserAdapter()).create();
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(gson.toJson(this));
		}
	}

	public static class ProjectAdapter implements JsonSerializer<Project> {

		@Override
		public JsonElement serialize(Project src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.getDirectory().getAbsolutePath());
		}
	}

	public static class PropertyParserAdapter implements JsonSerializer<PropertyParser> {

		@Override
		public JsonElement serialize(PropertyParser src, Type typeOfSrc, JsonSerializationContext context) {
			return new Gson().toJsonTree(src.getProperties());
		}
	}

	public List<Project> getProjects() {
		return projects == null ? new LinkedList<>() : projects;
	}

	public static class ProjectDeserializer implements JsonDeserializer<Project> {
		@Override
		public Project deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			if (new File(json.getAsString()).exists()) {
				try {
					ProjectDescriptor descriptor = ProjectDescriptor.load(new File(json.getAsString()));
					if (descriptor.getType() == null) {
						return new SimpleProject(descriptor);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}

	}

}
