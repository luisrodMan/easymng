package com.nxtr.easymng;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FireActionEvent {

	private Application application;
	private Object source;

}
