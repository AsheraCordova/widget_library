package com.ashera.widget;

public interface IWidgetLifeCycleListener {
	public enum EventId {measureFinished, onLayout};
	public void eventOccurred(EventId eventId, WidgetEvent event);
}
