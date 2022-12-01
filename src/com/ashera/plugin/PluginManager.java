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
