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
package com.ashera.validations;

import com.ashera.widget.IWidget;

public abstract class BaseValidator implements Validation {
	protected String errorMessage;
	@Override
	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getErrorMessage(IWidget widget) {
		if (errorMessage == null) {
			return getDefaultErrorMessage(widget);
		}
		return errorMessage;
	}

	protected String getDefaultErrorMessage(IWidget widget) {
		return null;
	}
}
