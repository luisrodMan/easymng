package com.nxtr.easymng;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ngeneration.furthergui.FMenu;
import com.ngeneration.furthergui.FMenuBar;
import com.ngeneration.furthergui.FMenuItem;
import com.ngeneration.furthergui.event.AbstractAction;
import com.ngeneration.furthergui.event.Action;
import com.ngeneration.furthergui.event.ActionEvent;

public class Setup {

	private static Gson gson = new Gson();

	public static void setMenu(FMenuBar menubar, InputStream stream, Map<String, Action> actions) {
		JsonObject root = null;
		try (InputStreamReader reader = new InputStreamReader(stream)) {
			root = gson.fromJson(new JsonReader(reader), JsonObject.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.get("menu").getAsJsonArray().forEach(jitem -> {
			menubar.add(parseItem(jitem.getAsJsonObject(), actions));
		});
	}

	private static FMenuItem parseItem(JsonObject object, Map<String, Action> actions) {
		JsonElement itemElement = object.get("items");
		String text = object.get("text").getAsString();
		JsonElement actionElement = object.get("action");
		FMenuItem item = null;
		if (itemElement == null)
			item = new FMenuItem(text);
		else {
			item = new FMenu(text);
			Iterator<JsonElement> iterator = itemElement.getAsJsonArray().iterator();
			FMenu menu = (FMenu) item;
			while (iterator.hasNext())
				menu.add(parseItem(iterator.next().getAsJsonObject(), actions));
		}
		if (actionElement != null)
			item.setAction(actions.get(actionElement.getAsString()));
		item.setText(text);
		return item;
	}

	/**
	 * Returns a case insensitive map.
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, Action> getActions(Object object) {
		System.out.println("actions: ");
		Map<String, Action> actions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for (Method method : object.getClass().getDeclaredMethods()) {
			String name = method.getName();
			if (name.endsWith("Action")) {
				System.out.println("action xd: " + name);
				final var name2 = name.substring(0, name.length() - "Action".length());
				actions.put(name2, new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent event) {
						System.out.println("action performed: " + name2);
						FireActionEvent arg = new FireActionEvent(Application.getInstance(), event.getSource());
						try {
							method.setAccessible(true);
							method.invoke(object, arg);
						} catch (Exception e2) {
							throw new RuntimeException(e2);
						}
					}
				});
			}
		}
		return actions;
	}

}
