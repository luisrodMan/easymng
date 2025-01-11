package com.nxtr.easymng.workspace2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nxtr.easymng.PropertyParser;

import lombok.Data;

@Data
public class ProjectDescriptor {

	private String file;
	private String version;
	private String name;
	private String type;
	private PropertyParser configuration;

	public static ProjectDescriptor load(File file) throws FileNotFoundException, IOException {
		try (FileReader reader = new FileReader(file)) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(PropertyParser.class, new PropertiesParserDeserializer());
			Gson gson = gsonBuilder.create();
			ProjectDescriptor val = gson.fromJson(reader, ProjectDescriptor.class);
			val.setFile(file.getAbsolutePath());
			return val;
		}
	}

}
