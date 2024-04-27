package com.ashera.converter;

import java.util.HashMap;
import java.util.Map;

public class FilterFactory {
	public static final String DEFAULT_FILTER = LowerCasePrefixFilter.class.getName();
	private static Map<String, IFilter> registrationMap = new HashMap<>();
	static {
		register(DEFAULT_FILTER, new LowerCasePrefixFilter());
	}
	public static IFilter get(String localname) {
		IFilter converter = registrationMap.get(localname);
		
		if (converter != null) {
			return converter;
		} 
		
		return null;
	}
	
	public static void register(String localname, IFilter converter) {
		registrationMap.put(localname, converter);
	}
	
}
