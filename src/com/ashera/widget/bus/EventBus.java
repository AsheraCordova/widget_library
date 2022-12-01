package com.ashera.widget.bus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventBus implements IEventBus {

	// A default Callback that does nothing.
	protected static final EventBusCallback _DEFAULT = new EventBusCallback() {
		@Override
		public void call() {
		}
	};

	private Map<String, CompositeHandler> _listeners = new HashMap<String, CompositeHandler>();

	public EventBus() {
	}

	@Override
	public void on(String type, EventBusHandler... handler) {
		if (!this.handles(type)) {
			_listeners.put(type, new CompositeHandler(type));
		}
		_listeners.get(type).add(handler);
		
	}

	@Override
	public void off(String type) {
		_listeners.remove(type);
	}

	@Override
	public boolean handles(String eventName) {
		return _listeners.containsKey(eventName);
	}

	@Override
	public void notifyObservers(String type, Object data) {
		if (this.handles(type)) {
			_listeners.get(type).perform(data);
		}
	}

	@Override
    public void off(EventBusHandler... widgetEventBusHandlers) {
        for (EventBusHandler eventBusHandler : widgetEventBusHandlers) {
            _listeners.get(eventBusHandler.getType()).remove(eventBusHandler);
        }
    }
	
	@Override
    public void off(List<EventBusHandler> widgetEventBusHandlers) {
        for (EventBusHandler eventBusHandler : widgetEventBusHandlers) {
            _listeners.get(eventBusHandler.getType()).remove(eventBusHandler);
        }
        
    }

	@Override
	public void offAll() {
		_listeners.clear();
	}

}
