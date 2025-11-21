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
package com.ashera.plugin;

import java.util.HashMap;
import java.util.Map;

public class PluginManager {
	private static Map<String, IPlugin> registrationMap = new HashMap<String, IPlugin>();
	public static void register(IPlugin plugin) {
		registrationMap.put(plugin.getName(), plugin);
	}
	
	public static IPlugin get(String name) {
		IPlugin plugin = registrationMap.get(name);
		if (plugin == null) {
			throw new RuntimeException("Unable to find registration for plugin: " + name);
		}
		return plugin;
	}
}
