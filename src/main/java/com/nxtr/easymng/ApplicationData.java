package com.nxtr.easymng;

import java.util.List;

public interface ApplicationData {

	String getVersion();

	String getActiveWorkspace();

	List<String> getRecents();

	PropertyParser getConfiguration();

}
