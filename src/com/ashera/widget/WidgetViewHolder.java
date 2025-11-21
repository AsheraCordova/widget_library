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

import com.ashera.model.ModelExpressionParser.ModelPojoToUiHolder;

/**
 * View holder pattern for widgets like listview, recycleview. 
 */
public class WidgetViewHolder {
	// widget which will be lazily loaded using loadLazyWidget
	public IWidget widget;
	public List<AttributeViewHolder> attributes = new java.util.ArrayList<>();
	
	public static class AttributeViewHolder {
		// apply setAttribute on this widget for this attribute and model 
		public IWidget childWidget;
		public ModelPojoToUiHolder modelPojoToUiHolder;
		public WidgetAttribute widgetAttribute;
	}

}
