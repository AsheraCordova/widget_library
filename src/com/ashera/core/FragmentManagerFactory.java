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
package com.ashera.core;

import java.util.HashMap;
import java.util.Map;

public class FragmentManagerFactory {	
	private static Map<String, IFragmentManager> fragmentManagerMap = new HashMap<>();
	
	public static void registerManager(String decorator, IFragmentManager behavior) {
		fragmentManagerMap.put(decorator, behavior);
	}
	
	public static IFragmentManager getManager(String behavior) {
		IFragmentManager manager = fragmentManagerMap.get(behavior);
		
		if (manager != null) {
			return manager.newInstance();
		} 
		
		return null;
	}	
}
