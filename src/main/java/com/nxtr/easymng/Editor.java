package com.nxtr.easymng;

public interface Editor extends View {

	void doSave();

	void doSaveAs();

	boolean isDirty();

}
