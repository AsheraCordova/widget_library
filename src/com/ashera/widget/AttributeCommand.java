package com.ashera.widget;

public interface AttributeCommand {
	Object modifyValue(IWidget widget, Object nativeView, String phase, String attributeName, Object value);
	AttributeCommand newInstance(Object... args);
	void updateArgs(Object... args);
	void updatePhaseArgs(Object... args);
	int getPriority();
	void setPriority(int priority); 
	boolean executeAfterPostMeasure();
	Object getValue(String attributeName);
	String getId();
}
