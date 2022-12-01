package com.ashera.model;

import java.util.ArrayList;
import java.util.List;

public class MethodHandlerFactory {
	
	private static List<MethodHandler> registrations = new ArrayList<>();

	public static void init() {
		
	}
	public static List<MethodHandler> getRegistrations() {
		return registrations;
	}
	
	public static void register(MethodHandler methodHandler) {
		registrations.add(methodHandler);
	}
}
