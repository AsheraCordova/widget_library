package com.ashera.plugin;

public interface IPlugin {
	public String getName();
	public Object invoke(String name, Object... args);
}
