package com.ashera.widget;

public interface IAttributable {
	public IAttributable newInstance(IWidget widget);
	void loadAttributes(String localName);
	void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator);
	Object getAttribute(WidgetAttribute key, ILifeCycleDecorator decorator);
	String getLocalName();

}
