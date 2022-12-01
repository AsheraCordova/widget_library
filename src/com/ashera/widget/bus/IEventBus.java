package com.ashera.widget.bus;

import java.util.List;

/**
 *
 */
public interface IEventBus {
	public void on(String type, EventBusHandler ... handler);
	public void off(String type);
	public void off(EventBusHandler... widgetEventBusHandlers);
	public void off(List<EventBusHandler> widgetEventBusHandlers);
	public void offAll();

	public boolean handles(String string);

	void notifyObservers(String type, Object data);
}
