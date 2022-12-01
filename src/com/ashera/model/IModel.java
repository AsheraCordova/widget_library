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
