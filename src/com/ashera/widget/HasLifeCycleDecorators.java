package com.ashera.widget;

public interface HasLifeCycleDecorators {
	void addDecorator(ILifeCycleDecorator decorator);
	void executeMethodListeners(String methodName, Object... objectArray);
	void executeMethodListeners(String methodName, Runnable callback, Object... args);
	void setOnMethodCalled(boolean onMethodCalled);
	boolean hasMethodListener(String methodName);
}
