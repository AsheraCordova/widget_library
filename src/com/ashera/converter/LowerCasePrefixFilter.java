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

public class LowerCasePrefixFilter implements IFilter{

	@Override
	public boolean filter(String value, String query) {
		boolean matches = false;
		
		if (query == null || query.length() == 0) {
			return true;
		}
		if (value == null) {
			return false;
		}
		
		query = query.toLowerCase();
		// First match against the whole, non-splitted value
        if (value.toLowerCase().startsWith(query)) {
        	matches = true;
        } else {
            final String[] words = value.split(" ");
            for (String word : words) {
                if (word.toLowerCase().startsWith(query)) {
                	matches = true;
                    break;
                }
            }
        }
		return matches;
	}

}
