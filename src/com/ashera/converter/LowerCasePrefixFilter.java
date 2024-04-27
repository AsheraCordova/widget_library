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
