package com.nxtr.easymng.view;

import java.util.List;

import com.ngeneration.furthergui.FComponent;
import com.nxtr.easymng.View;

public interface ViewContainer {
	
	List<View> getViews();
	
	void addView(View view);
	
	boolean contains(View view);
	
	FComponent getComponent();

}
