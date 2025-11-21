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
package com.ashera.widget.bus;

public class Event {
	public enum StandardEvents {
		orientationChange, customEvent, initialise, orientationChangePostParentMeasure, postMeasure, preMeasure, dealloc, outsideClicked;
	}
	private StandardEvents type;
	private Object additionalData;
	
	public Event(StandardEvents type, Object additionalData) {
		super();
		this.type = type;
		this.additionalData = additionalData;
	}

	public Event(StandardEvents type) {
		super();
		this.type = type;
	}

	public StandardEvents getType() {
		return type;
	}

	public void setType(StandardEvents type) {
		this.type = type;
	}

	public Object getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Object additionalData) {
		this.additionalData = additionalData;
	}
	
}
