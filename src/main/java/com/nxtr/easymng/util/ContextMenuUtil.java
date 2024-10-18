package com.nxtr.easymng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ngeneration.furthergui.FMenuItem;
import com.ngeneration.furthergui.FPopupMenu;
import com.ngeneration.furthergui.event.Action;

import lombok.Data;

public class ContextMenuUtil {

	@Data
	public static class ContextMenuItemDef {
		private String text;
		private String action;
		private final List<String> filters = new LinkedList<>();
	}

	@Data
	public static class ContextMenuDef {
		private final List<ContextMenuItemDef> items = new LinkedList<>();
	}

	public static ContextMenuDef load(InputStream stream) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			return new Gson().fromJson(reader, ContextMenuDef.class);
		}
	}

	public static ContextMenuDef setup(FPopupMenu popupMenu, InputStream stream, Map<String, Action> actions) throws IOException {
		var defs = load(stream);
		defs.items.forEach(itemInfo -> {
			FMenuItem item = new FMenuItem(itemInfo.getText());
			var action = actions.get(itemInfo.getAction());
			if (action != null)
				item.addActionListener(action);
			else
				System.out.println("action not found: " + itemInfo.getAction());
			popupMenu.add(item);
		});
		return defs;
	}

}
