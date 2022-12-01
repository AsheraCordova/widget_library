package com.ashera.widget;

public interface EventCommand {
	Object executeCommand(IWidget widget, java.util.Map<String, Object> eventObject, Object... params);
}
