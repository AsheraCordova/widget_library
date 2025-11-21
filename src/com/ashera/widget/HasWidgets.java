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

import com.ashera.model.IModelGroup;
import com.ashera.model.LoopParam;

public interface HasWidgets extends IWidget, IModelGroup {
    // remove/add the delete widgets
	void add(IWidget w, int index);
	void clear();
	IWidget get(int index);
	List<IWidget> getWidgets();
	java.util.Iterator<IWidget> iterator();
	boolean remove(IWidget w);
	boolean remove(int index);
	
	// template for repeating the widgets
	void addTemplate(Object objValue);
	   
    //  load lazy widgets - template widget to be loaded dynamically
    Object getChildAttribute(IWidget w, WidgetAttribute key);
	void setChildAttribute(IWidget widget, WidgetAttribute key, String strValue, Object objValue);
	
    HasWidgets getCompositeLeaf(IWidget w);
    boolean areWidgetItemsRecycled();
}
