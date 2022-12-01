package com.ashera.model;

import com.ashera.widget.IWidget;

public interface MethodHandler {
	String UNHANDLED = "~~UNHANDLER~~";
	Object handle(String methodName, Object obj, IWidget widget);

}
