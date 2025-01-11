package com.nxtr.easymng;

import com.nxtr.easymng.workspace2.Workspace;

public interface ApplicationListener {

	void onWorkspaceChanged(Application application, Workspace old);

}
