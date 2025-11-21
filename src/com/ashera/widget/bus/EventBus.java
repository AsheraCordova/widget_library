//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package com.ashera.widget.bus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventBus implements IEventBus {
	private List<EventBus> childEventBusses;
	// A default Callback that does nothing.
	protected static final EventBusCallback _DEFAULT = new EventBusCallback() {
		@Override
		public void call() {
		}
	};

	private Map<String, CompositeHandler> _listeners = new HashMap<String, CompositeHandler>();

	public EventBus() {
	}
	
	public void addEventBus(EventBus eventBus) {
		if (childEventBusses == null) {
			childEventBusses = new java.util.ArrayList<>();
		}
		
		if (!childEventBusses.contains(eventBus)) {
			childEventBusses.add(eventBus);
		}
	}
	
	public void removeEventBus(EventBus eventBus) {
		if (childEventBusses != null) {
			childEventBusses.remove(eventBus);
		}
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
		
		if (childEventBusses != null) {
			for (EventBus eventBus : childEventBusses) {
				eventBus.notifyObservers(type, data);
			}
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
