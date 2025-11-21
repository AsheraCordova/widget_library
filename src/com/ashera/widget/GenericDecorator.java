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

public class GenericDecorator implements IDecorator{
	private static final String LOCAL_NAME = "generic";
	private IWidget wrappedWidget; 
	private ICompositeDecorator wrappingWidget;
	private List<String> supportedAttributes;

	@Override
	public void setSupportedAttributes(List<String> supportedAttributes) {
		this.supportedAttributes = supportedAttributes;
	}

	@Override
	public IDecorator newInstance(ICompositeDecorator wrappingWidget, IWidget wrappedWidget) {	
		GenericDecorator decorator = new GenericDecorator();
		decorator.wrappedWidget =  wrappedWidget;
		decorator.wrappingWidget = wrappingWidget;
		return decorator;
	}
	
	@Override
	public IWidget getWrappedWidget() {
		return wrappedWidget;
	}


	@Override
	public void create(Map<String, Object> metadata) {
	}

	@Override
	public boolean setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator lifeCycleDecorator) {
		if (supportedAttributes.contains(key.getAttributeName())) {
			wrappingWidget.setAttribute(key, strValue, objValue, lifeCycleDecorator, true);
			return true;
		}
		
		return false;
		
	}

	@Override
	public void initialized() {
	}

	@Override
	public String getLocalName() {
		return LOCAL_NAME;
	}

	@Override
	public List<String> getSupportedAttributes() {
		return supportedAttributes;
	}

}
