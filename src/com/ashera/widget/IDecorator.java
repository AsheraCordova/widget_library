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
