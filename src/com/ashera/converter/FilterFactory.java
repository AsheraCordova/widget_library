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
