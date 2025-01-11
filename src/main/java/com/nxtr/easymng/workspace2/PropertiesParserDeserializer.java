package com.nxtr.easymng.workspace2;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.nxtr.easymng.PropertyParser;

public class PropertiesParserDeserializer implements JsonDeserializer<PropertyParser> {
	@Override
	public PropertyParser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return PropertyParser.getParser(json);
	}

}