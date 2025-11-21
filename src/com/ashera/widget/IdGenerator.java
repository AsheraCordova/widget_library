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
package com.ashera.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class IdGenerator {
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	private static Map<String, Integer> idMap = new HashMap<>();
	private static Map<Integer, String> reverseMap = new HashMap<>();
	public static String getName(int id) {
		return reverseMap.get(id);
	}
	
	public static void setId(String name, int id) {
		idMap.put(name, id);
		reverseMap.put(id, name);
	}
	
	/**
	 * Generate a value suitable for use in {@link #setId(int)}.
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	public static int getId(String id) {
		if (id != null && id.startsWith("@id/")) {
			id = "@+id/" + id.substring(4);
		}
		if (idMap.containsKey(id)) {
			return idMap.get(id);
		}
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	        	idMap.put(id, result);
	        	reverseMap.put(result, id);
	            return result;
	        }
	    }
	}
}
