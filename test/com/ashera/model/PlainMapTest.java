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
package com.ashera.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlainMapTest {
	public static void main(String[] args) {
		Map<String, Object> x = new HashMap<>();
		x.put("a", "test");
		ArrayList<Map<String, Object>> strs = new ArrayList<>();
		
		Map<String, Object> y = new HashMap<>();
		y.put("a", "test");
		
		strs.add(y);
		strs.add(y);
		x.put("aa", strs);
		
		PlainMap plainMap2 = new PlainMap(x);
		Map<String, String> plainMap = plainMap2.getPlainMap();
		System.out.println(plainMap);
//		PlainMap plainMap = new PlainMap(x);
//		System.out.println( ((List)plainMap.get("aa")).get(0).getClass());
		//((List)plainMap.get("aa")).set(0, new PlainMap((Map<String, Object>) ((List)plainMap.get("aa")).get(0)));
		((List)plainMap2.get("aa")).add(y);
		System.out.println(plainMap2.getHierarchicalMap());
	}
}
