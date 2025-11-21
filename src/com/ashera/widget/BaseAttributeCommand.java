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

public abstract class BaseAttributeCommand implements AttributeCommand {
	protected int priority;
	protected String phase;
	protected String id;

	public BaseAttributeCommand(String id) {
		super();
		this.id = id;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public boolean executeAfterPostMeasure() {
		return false;
	}

	@Override
	public void updatePhaseArgs(Object... args) {
	}
	
	@Override
	public Object getValue(String attributeName) {
		return null;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
