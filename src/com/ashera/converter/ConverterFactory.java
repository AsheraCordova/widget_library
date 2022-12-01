package com.ashera.converter;

import java.util.HashMap;
import java.util.Map;

import com.ashera.widget.AttributeCommand;

public class ConverterFactory {
	private static Map<String, IConverter> registrationMap = new HashMap<>();
	private static Map<String, AttributeCommand> commandConverters = new HashMap<>();

	public static IConverter get(String localname) {
		IConverter converter = registrationMap.get(localname);
		
		if (converter != null) {
			return converter;
		} 
		
		return null;
	}
	
	public static void register(String localname, IConverter converter) {
		registrationMap.put(localname, converter);
	}
	
	public static AttributeCommand getCommandConverter(String localname, Object... args) {
		AttributeCommand converter = commandConverters.get(localname);
		
		if (converter != null) {
			return converter.newInstance(args);
		} 
		
		return null;
	}
	
	public static void registerCommandConverter(AttributeCommand converter) {
		commandConverters.put(converter.getId(), converter);
	}
}
