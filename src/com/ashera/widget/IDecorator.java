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

import java.util.List;
import java.util.Map;


public interface IDecorator {
	String getLocalName();
	IWidget getWrappedWidget();
	public IDecorator newInstance(ICompositeDecorator wrappingDecorator, IWidget wrappedWidget);
	public void create(Map<String, Object> metadata);
	boolean setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator lifeCycleDecorator);
	void initialized();
	List<String> getSupportedAttributes();
	void setSupportedAttributes(List<String> supportedAttributes);
}
