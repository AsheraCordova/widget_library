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
package com.ashera.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.ashera.core.IFragment;

public class ResourceBundleUtils {
	private static java.util.HashMap<String, ResourceBundle> cache = new java.util.HashMap<>();
	private static java.util.HashMap<String, Properties> cacheByRootDir = new java.util.HashMap<>();
	
	public static abstract class GetPropertiesTemplate {
		String getValue(String bundle, String prefix, String resourceOrText, IFragment fragment) {
			String resPrefix = "@" + prefix + "/";
			Object properties = loadProperties();
			if (resourceOrText.startsWith(resPrefix)) {
				String value = null;
				
				while (resourceOrText.startsWith(resPrefix)) {
					String key1 = resourceOrText.replaceFirst(resPrefix, "");
					if (containsKey(properties, key1)) {
						value = getProperty(properties, key1);
						resourceOrText = value;
					} else {
						break;
					}
				}
				return value;
			}
			
			return resourceOrText;
		}

		protected abstract String getProperty(Object properties, String key);
		protected abstract boolean containsKey(Object properties, String key);

		public abstract Object loadProperties();
	}
	public static String getString(String bundle, String prefix, String resourceOrText, IFragment fragment) {
		if (resourceOrText == null) {
			return null;
		}
		
		String rootDirectory = fragment.getRootDirectory();
		if (rootDirectory != null) {		
			return getString(bundle, prefix, resourceOrText, rootDirectory, fragment);			
		} else if (fragment.hasDevData(bundle)) {
			return new GetPropertiesTemplate() {
				@Override
				protected String getProperty(Object properties, String key) {
					return ((Properties) properties).getProperty(key);
				}

				@Override
				protected boolean containsKey(Object properties, String key) {
					return ((Properties) properties).containsKey(key);
				}

				@Override
				public Object loadProperties() {
					Properties properties = (Properties) fragment.getDevData(bundle);
					return properties;
				}
				
			}.getValue(bundle, prefix, resourceOrText, fragment);	
	    } else {
	    	return new GetPropertiesTemplate() {
				@Override
				protected String getProperty(Object properties, String key) {
					return ((ResourceBundle) properties).getString(key);
				}

				@Override
				protected boolean containsKey(Object properties, String key) {
					return ((ResourceBundle) properties).containsKey(key);
				}

				@Override
				public Object loadProperties() {
					ResourceBundle resourceBundle = getRS(bundle);
					return resourceBundle;
				}
				
			}.getValue(bundle, prefix, resourceOrText, fragment);	
	    }
	}

	public static String getString(String bundle, String prefix, String resourceOrText, String rootDirectory,
			IFragment fragment) {
		return new GetPropertiesTemplate() {

			@Override
			protected String getProperty(Object properties, String key) {
				return ((Properties) properties).getProperty(key);
			}

			@Override
			protected boolean containsKey(Object properties, String key) {
				return ((Properties) properties).containsKey(key);
			}

			@Override
			public Object loadProperties() {
				Properties properties = cacheByRootDir.get(rootDirectory + bundle);
				if (properties == null) {
					properties = loadMergedProperties(bundle, Locale.getDefault(), rootDirectory, fragment);
					cacheByRootDir.put(rootDirectory + bundle, properties);
				}
				return properties;
			}
			
		}.getValue(bundle, prefix, resourceOrText, fragment);
	}

	private static ResourceBundle getRS(String bundle) {
		Locale locale = Locale.getDefault();
		String cacheKey = bundle + locale.toString();
		if (cache.containsKey(cacheKey)) {
			return cache.get(cacheKey);
		}
		ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle, locale);
		cache.put(cacheKey, resourceBundle);
		return resourceBundle;
	}
	
	public static String getString(String bundle, String key, IFragment fragment) {
		String value = null;
		if (fragment != null && fragment.hasDevData(bundle)) {
			Properties properties = (Properties) fragment.getDevData(bundle);
			value = (String) properties.get(key);
		} else if (fragment != null && fragment.getRootDirectory() != null) {
			Properties properties = getPropertiesUsingCache(fragment.getRootDirectory(), bundle, fragment);
			if (properties.containsKey(key)) {
				value = properties.getProperty(key);
			}
		} else {
			ResourceBundle resourceBundle = getRS(bundle);
			if (resourceBundle.containsKey(key)) {
				value = resourceBundle.getString(key);
			}
		}
		return value;
	}

	private static Properties getPropertiesUsingCache(String directoryPath, String bundle, IFragment fragment) {
		Properties properties = cacheByRootDir.get(fragment.getRootDirectory() + bundle);
		if (properties == null) {
			properties = getProperties(directoryPath, bundle + ".properties", fragment);			
			cacheByRootDir.put(fragment.getRootDirectory() + bundle, properties);
		}
		return properties;
	}
	
	
	 private static Properties loadMergedProperties(String baseName, Locale locale, String directoryPath, IFragment fragment)  {
        List<Locale> fallbackLocales = getLocaleFallbackChain(locale);
        Properties mergedProps = new Properties();

        for (int i = fallbackLocales.size() - 1; i >= 0; i--) {
            Locale loc = fallbackLocales.get(i);
            String suffix = toSuffix(loc);
            String fileName = baseName + suffix + ".properties";

            Properties props = getProperties(directoryPath, fileName, fragment);
        	mergedProps.putAll(props);
        }

        return mergedProps;
    }

	private static Properties getProperties(String directoryPath, String fileName, IFragment fragment) {
		String propertyData = com.ashera.widget.PluginInvoker.readCdvDataAsString(directoryPath, "resources/" + fileName, fragment);
		Properties props = readStringAsProperties(propertyData);
		return props;
	}

	public static Properties readStringAsProperties(String propertyData) {
		Properties props = new Properties();
		if (propertyData != null) {
		    try (java.io.StringReader stringReader = new java.io.StringReader(propertyData)) {
		        try {						
					props.load(stringReader);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		    }
		}
		return props;
	}

    private static List<Locale> getLocaleFallbackChain(Locale locale) {
        List<Locale> locales = new ArrayList<>();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();

        if (!variant.isEmpty()) locales.add(new Locale(language, country, variant));
        if (!country.isEmpty()) locales.add(new Locale(language, country));
        if (!language.isEmpty()) locales.add(new Locale(language));
        locales.add(Locale.ROOT);

        return locales;
    }

    private static String toSuffix(Locale locale) {
        if (locale.equals(Locale.ROOT)) return "";
        StringBuilder sb = new StringBuilder();
        if (!locale.getLanguage().isEmpty()) sb.append("_").append(locale.getLanguage());
        if (!locale.getCountry().isEmpty()) sb.append("_").append(locale.getCountry());
        if (!locale.getVariant().isEmpty()) sb.append("_").append(locale.getVariant());
        return sb.toString();
    }
}
