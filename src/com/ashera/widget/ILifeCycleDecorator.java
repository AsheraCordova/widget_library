package com.ashera.widget;

import java.util.List;


public interface ILifeCycleDecorator {
	void execute(String method, Object... params);
	List<String> getMethods();
	ILifeCycleDecorator newInstance(IWidget  widget);
	void setAttribute(WidgetAttribute widgetAttribute, String strValue, Object objValue);
	void initialized();
    Object getAttribute(WidgetAttribute widgetAttribute);
    void drawableStateChanged();
    IWidget getWidget();
}
