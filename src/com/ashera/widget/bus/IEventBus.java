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
