package com.nxtr.easymng;

import java.util.LinkedList;
import java.util.List;

import com.nxtr.easymng.workspace.IWorkspaceItem;

public class ViewResolverManager {

	private List<IViewResolver> viewResolvers = new LinkedList<>();

	public View resolve(IWorkspaceItem model) {
		// default editors?
		// defaults.get(model.getClass());

		// file resolver
//		if (model instanceof IFileWorkspace file)// based on extension
//			extensionResolversMap.get(file.getExtensionXD);
//			if any any.resolve

		var resolver = viewResolvers.stream().filter(r -> r.canHandle(model)).findAny();
		return resolver.isEmpty() ? null : resolver.get().getView(model);
	}

	public void addViewResolver(IViewResolver classViewResolver) {
		viewResolvers.add(classViewResolver);
	}

}
