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
