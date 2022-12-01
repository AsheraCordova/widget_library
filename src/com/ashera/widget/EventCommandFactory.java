package com.ashera.widget;

import java.util.HashMap;
import java.util.Map;

public class EventCommandFactory {
	private static Map<String, EventCommand> commandMap = new HashMap<String, EventCommand>();
	
	public static void registerCommand(String name, EventCommand eventCommand) {
		commandMap.put(name, eventCommand);
	}
	
	public static EventCommand getCommand(String name) {
		EventCommand eventCommand = commandMap.get(name);
		return eventCommand;
	}
	
	public static boolean hasCommand(String name) {
		return commandMap.containsKey(name);
	}
}
