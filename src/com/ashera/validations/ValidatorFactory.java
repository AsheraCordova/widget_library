package com.ashera.validations;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {

	
	private static Map<String, Validation> registrationMap = new HashMap<>();

	public static void init() {
		
	}
	public static Validation get(String localname) {
		Validation validator = registrationMap.get(localname);
		
		if (validator != null) {
			return validator;
		} 
		
		return null;
	}
	
	public static void register(String localname, Validation validator) {
		registrationMap.put(localname, validator);
	}
}
