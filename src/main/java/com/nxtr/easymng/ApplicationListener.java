package com.nxtr.easymng;

import com.nxtr.easymng.workspace.WorkspaceInfo;

public interface ApplicationListener {
	
	void onWorkspaceChanged(Application application, WorkspaceInfo oldActiveWorkspace);

}
