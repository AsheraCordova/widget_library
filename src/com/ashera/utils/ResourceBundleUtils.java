package com.ashera.utils;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.ashera.core.IFragment;

public class ResourceBundleUtils {
	private static java.util.HashMap<String, ResourceBundle> cache = new java.util.HashMap<>();
	public static String getString(String bundle, String prefix, String resourceOrText, IFragment fragment) {
		if (resourceOrText == null) {
			return null;
		}
	    if (fragment.hasDevData(bundle)) {
	        String resPrefix = "@" + prefix + "/";
	        if (resourceOrText.startsWith(resPrefix)) {
	            Properties properties = (Properties) fragment.getDevData(bundle);
	            String value = null;

	            while (resourceOrText.startsWith(resPrefix)) {
	                String key1 = resourceOrText.replaceFirst(resPrefix, "");
	                if (properties.containsKey(key1)) {
	                	value = properties.getProperty(key1);
	                	resourceOrText = value;
	                } else {
	                	break;
	                }
	            }
	            return value;
	        } 
	    } else {
    		String resPrefix = "@" + prefix + "/";
    		if (resourceOrText.startsWith(resPrefix)) {
    			ResourceBundle resourceBundle = getRS(bundle);
    			String value = null;
    
    			while (resourceOrText.startsWith(resPrefix)) {
    				String key1 = resourceOrText.replaceFirst(resPrefix, "");
    				if (resourceBundle.containsKey(key1)) {
	    				value = resourceBundle.getString(key1);
	    				resourceOrText = value;
    				}  else {
	                	break;
	                }
    			}
    			return value;
    		} 
	    }
	    
	    return resourceOrText;
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
		} else {
			ResourceBundle resourceBundle = getRS(bundle);
			if (resourceBundle.containsKey(key)) {
				value = resourceBundle.getString(key);
			}
		}
		return value;
	}
}
