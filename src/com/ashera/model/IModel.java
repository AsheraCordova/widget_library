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
package com.ashera.model;

import java.util.List;
import java.util.Map;

import com.ashera.widget.bus.EventBusHandler;

public interface IModel {
	String getModelIdPath();
    String getModelSyncEvents();
    String getModelParam();
    String getModelPojoToUi();
    String getModelPojoToUiParams();
    String getModelUiToPojoEventIds();
    String getModelUiToPojo();
    LoopParam getLoopParam();
    Object getModelFromScope(String key, ModelScope varScope);
    void storeModelToScope(String varName, ModelScope varScope, Object objValue);

	void setModelIdPath(String idPath);
	void setModelSyncEvents(String modelSyncEvents);
	void setModelParam(String param);
	void setModelPojoToUi(String val);
	void setModelPojoToUiParams(String params);
	void setModelUiToPojo(String val);
	void setModelUiToPojoEventIds(String val);
	void setLoopParam(LoopParam loopParam);
    
	
    // event handling
	void syncModelFromUiToPojo(String eventType);
	void updateModelToEventMap(Map<String, Object> eventMap, String eventType, String eventParams);
    
    List<EventBusHandler> getEventBusHandlers();
    void notifyDataSetChanged();
    
	void updateModelData(String expression, Object data);
	void applyModelToWidget();	
	Object getModelByPath(String varPath, Object obj);
	void updateModelByPath(String varPath, Object objValue, Object obj);
}
