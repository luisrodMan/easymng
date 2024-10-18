package com.nxtr.easymng;

import java.util.function.Function;

import com.nxtr.easymng.workspace.IWorkspaceItem;

public class ClassViewResolver<T> implements IViewResolver {

	private Function<T, View> consumer;
	private Class<T> clazz;

	public ClassViewResolver(Class<T> clazz, Function<T, View> consumer) {
		this.clazz = clazz;
		this.consumer = consumer;
	}

	@Override
	public boolean canHandle(IWorkspaceItem item) {
		return clazz.isAssignableFrom(item.getClass());
	}

	@Override
	public View getView(IWorkspaceItem item) {
		return consumer.apply((T) item);
	}

}
