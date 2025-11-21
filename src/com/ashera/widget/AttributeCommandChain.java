//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package com.ashera.widget;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class AttributeCommandChain {
	private Object originalValue;
	private String originalStringValue;
	private String attributeName;
	private Set<AttributeCommand> commands;

	public void addCommand(AttributeCommand command) {
		if (commands == null) {
			commands = new TreeSet<>(new Comparator<AttributeCommand>() {
				@Override
				public int compare(AttributeCommand o1, AttributeCommand o2) {
					if (o1 == null || o1 == null) {
						return 0;
					}
					int p = o1.getPriority() - o2.getPriority();
					if (p == 0) {
						return o1.getClass().getName().compareTo(o2.getClass().getName());
					}
					return p;
				}
				
			});
		}
		commands.add(command);
	}

	public Object getValue(IWidget widget) {
		return getValue(widget, null, null);
	}

	public Object getValue(IWidget widget, Object nativeWidget, String phase, Object... args) {
		return getValue(widget, nativeWidget, phase, null, args);
	}
	public Object getValue(IWidget widget, Object nativeWidget, String phase, String commandFilterRegex, Object... args) {
		Object finalValue = originalValue;
		
		if (commands != null) {
			for (AttributeCommand command : commands) {
				if (commandFilterRegex == null || command.getId().matches(commandFilterRegex)) {
					if ((phase == null && !command.executeAfterPostMeasure()) || (phase != null)) {
						if (phase != null && args != null && args.length > 0) {
							// this is needed as we might need a paint object which only available during the pre-draw or post draw phase
							command.updatePhaseArgs(args);
						}
						finalValue = command.modifyValue(widget, nativeWidget, phase, attributeName, finalValue);
					}
				}
			}
		}
		
		return finalValue;
	}
	
	public Object getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}
	public void removeCommand(AttributeCommand executor) {
		if (commands != null && executor != null) {
			commands.remove(executor);
		}
	}
	
	public String getOriginalStringValue() {
		return originalStringValue;
	}
	public void setOriginalStringValue(String originalStringValue) {
		this.originalStringValue = originalStringValue;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
}
