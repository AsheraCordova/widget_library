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

import java.util.List;

import com.ashera.widget.IWidget;


/**
 * Wrapper interface for views
 */
public interface FormElement extends IWidget {	
	String getFormGroupId();
    String getTextEntered();
    boolean isViewVisible();
    void focus();
	void resetError();
	void showError(String message);
	void setCustomErrorMessageKeys(List<String> customErrorMessageKeys);
	void setCustomErrorMessageValues(List<String> customErrorMessageValues);
	String getCustomMessage(String key);
	
	final static int ERROR_DISPLAY_TYPE_POPUP = 0x1;
	final static int ERROR_DISPLAY_TYPE_LABEL = 0x2;
	final static int ERROR_DISPLAY_TYPE_STYLE = 0x4;
	int getValidationErrorDisplayType();
	void setValidationErrorDisplayType(int validationErrorDisplayType);
	
	void setNormalStyle(String style);
	void setErrorStyle(String errorStyle);
}
