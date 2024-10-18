package com.nxtr.easymng;

import com.ngeneration.furthergui.FComponent;

public interface View {

	String getId();

	String getTitle();

	FComponent getComponent();

	void restore(Bundle bundle);

	void onAttached(Bundle bundle);

	void save(Bundle bundle);
}
