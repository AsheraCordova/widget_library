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

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ashera.model.ModelExpressionParser.ModelFromScopeHolder;
import com.ashera.widget.IWidget;

public class ExpressionMethodHandler {

	public static Object getValue(String methodName, Object obj, IWidget widget) {
		String params = widget.getModelPojoToUiParams();
		Map<String, Object> configMap = null;
		if (params != null) {
			configMap = ModelExpressionParser.parseSimpleCssExpression(params);
		}
		Object val = null;
		switch (methodName) {
		case "toString": {
			val = com.ashera.widget.PluginInvoker.getString(obj);
			if (val == null && obj != null) {
				val = obj.toString();
			}
			break;
		}
		case "not": {
			val = not(obj);
			break;
		}
		case "ternary": {
			val = ternary(widget, obj, configMap);
			break;
		}
		case "listToString": {
			val = listToString(obj, configMap);
			break;
		}
		case "concat": {
			val = concat(obj, widget, configMap);
			break;
		}
		case "size": {
			val = size(obj);
			break;
		}
		case "visible": {
			val = isVisible(obj) ? "visible" : "gone";
			break;
		}
		case "gone": {
			val = isVisible(obj) ? "gone" : "visible";
			break;
		}
		case "getDescFromModel": {
			val = getDescFromModel(obj, widget, configMap);
			break;
		}
		case "formatString": {
			val = formatString(obj, widget, configMap);
			break;
		}
		case "getDescFromRes": {
			val = getDescFromRes(obj, widget, configMap);
			break;
		}
		
		case "multiply": {
			val = multiply(widget, obj, configMap);
			break;
		}
		case "sum": {
			val = sum(widget, obj, configMap);
			break;
		}
		case "total": {
			val = total(widget, obj, configMap);
			break;
		}
		case "baseElapsedTimeInMillis": {
			val = baseElapsedTimeInMillis(widget, obj, configMap);
			break;
		}
		case "getFileAsset": {
			val = getFileAsset(obj, widget, configMap);
			break;
		}
		default:
			List<MethodHandler> handlers = MethodHandlerFactory.getRegistrations();
			for (MethodHandler methodHandler : handlers) {
				Object result = methodHandler.handle(methodName, obj, widget);
				
				if (result != MethodHandler.UNHANDLED) {
					val = result;
					break;
				}
			}
			break;
		}
		
		if (configMap != null && configMap.containsKey("numberFormat")) {
			if (val instanceof String) {
				val = Double.parseDouble((String) val);
			}
			java.text.DecimalFormat d = new java.text.DecimalFormat((String) configMap.get("numberFormat"));
			val = d.format(val);
		}
		
		if (configMap != null && configMap.containsKey("dateFormat")) {
			java.text.SimpleDateFormat d = new java.text.SimpleDateFormat((String) configMap.get("dateFormat"));
			val = d.format(val);
		}
		
		ModelDataType modelDataType = null;
		if (configMap != null) {
			String finalResultType = (String) configMap.get("varType");
			if (finalResultType != null) {
				modelDataType = ModelDataType.valueOf(finalResultType);
			}
		}
		
		if (modelDataType == null) {
			modelDataType = ModelDataType.string;
		}
		val = ModelStore.changeModelDataType(modelDataType, val);
		
		if (configMap != null) {
			String varPath = (String) configMap.get("varPath");
			if (varPath != null) {
				widget.updateModelByPath(varPath, val, obj);
			}
		}
		return val;
	}

	private static Object sum(IWidget widget, Object obj, Map<String, Object> configMap) {
		String[] fields = ((String) configMap.get("fields")).split(",");
		float sum = 0;
		
		for (String path : fields) {
			sum += (com.ashera.widget.PluginInvoker.getFloat(widget.getModelByPath(path, obj)));
		}
		return sum;
	}

	private static Object ternary(IWidget widget, Object obj, Map<String, Object> configMap) {
		Object positive = configMap.get("true");
		Object negtive = configMap.get("false");
		Object result = negtive;
		if ((obj instanceof Boolean && (Boolean) obj)) {
			result = positive;
		}
		
		if (com.ashera.widget.PluginInvoker.getBoolean(obj)) {
			result = positive;
		}
		
		return widget.quickConvert(result, "resourcestring");
	}

	private static Object baseElapsedTimeInMillis(IWidget widget, Object obj, Map<String, Object> configMap) {
		String dateFormat = "dd/MM/yyyy HH:mm";
		int defaultValue = 0;
		boolean allowNegativeValues = false;
		if (configMap != null) {
			if (configMap.containsKey("dateFormat")) {
				dateFormat = (String) configMap.get("dateFormat");
			}
			
			if (configMap.containsKey("defaultValue")) {
				defaultValue = Integer.parseInt((String) configMap.get("defaultValue"));
			}
			
			if (configMap.containsKey("allowNegativeValues")) {
				allowNegativeValues = configMap.get("allowNegativeValues").equals("true");
			}
		}

		if (obj == null) {
			return defaultValue;
		}
		
		java.text.SimpleDateFormat sdateFormat = new java.text.SimpleDateFormat(dateFormat);
		
		try {
			Date d = sdateFormat.parse((String) obj);
			int timeInMillis = (int) (d.getTime() - System.currentTimeMillis());
			if (!allowNegativeValues && timeInMillis < 0) {
				timeInMillis = 0;
			}
			return timeInMillis; 
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private static Object getDescFromRes(Object obj, IWidget widget, Map<String, Object> configMap) {
		if (configMap != null) {
			String delimiter = getDelimiter(configMap);
			List<String> entries = (List<String>) widget.quickConvert("@array/" + configMap.get("entries"), "array", "resourcestring", null);
			List<String> values = (List<String>) widget.quickConvert("@array/" + configMap.get("values"), "array", "resourcestring", null);
			List<String> descs = new java.util.ArrayList<>();
			List<Object> listStrs = com.ashera.widget.PluginInvoker.getList(obj);
			
			if (listStrs == null) {
				listStrs = new java.util.ArrayList<>();
				listStrs.add(obj);
			}
			
			for (Object listStr : listStrs) {
				for (int i = 0; i < values.size(); i++) {
					String value = values.get(i);
					if (value.equals(listStr)) {
						descs.add(entries.get(i));
					}
				}
			}
			
			
			return String.join(delimiter, descs.toArray(new String[0]));
		}
		return "";
	}

	private static Object formatString(Object obj, IWidget widget, Map<String, Object> configMap) {
		if (configMap != null) {
			String format = (String) widget.quickConvert(configMap.get("format"), "resourcestring");
			String[] fields = ((String) configMap.get("fields")).split(",");
			
			List<String> objParams = new java.util.ArrayList<>();
			for (String path : fields) {
				objParams.add(com.ashera.widget.PluginInvoker.getString(widget.getModelByPath(path, obj)));
			}
			return String.format(format, objParams.toArray());
		}
		return "";
	}

	private static Object getDescFromModel(Object obj, IWidget widget, Map<String, Object> configMap) {
		if (configMap != null) {
			ModelFromScopeHolder modelFromScopeHolder = ModelExpressionParser.parseModelFromScope((String) configMap.get("scope"));
			List<Object> dropDownVals = com.ashera.widget.PluginInvoker.getList(((com.ashera.widget.BaseWidget) widget).getModelByPath(modelFromScopeHolder.varPath, 
					widget.getModelFromScope(modelFromScopeHolder.varName, modelFromScopeHolder.varScope)));
			String delimiter = getDelimiter(configMap);
			if (dropDownVals instanceof List) {
				List<Object> listStrs = com.ashera.widget.PluginInvoker.getList(obj);
				List<String> descs = new java.util.ArrayList<>();
				
				if (listStrs == null) {
					listStrs = new java.util.ArrayList<>();
					listStrs.add(obj);
				}
				for (Object listStr : listStrs) {
					for (Object dropDownVal : dropDownVals) {
						Object id = widget.getModelByPath((String) configMap.get("id"), dropDownVal);
						if (id != null && id.equals(listStr)) {
							descs.add((String) widget.getModelByPath((String) configMap.get("value"), dropDownVal));
						}
					}
				}
				
				return String.join(delimiter, descs.toArray(new String[0]));
			}
		}
		return "";
	}

	private static Object size(Object obj) {
		if (obj instanceof List) {
			return ((List) obj).size() + "";
		}
		return "0";
	}

	private static Object concat(Object obj, IWidget widget, Map<String, Object> configMap) {
		String result = "";
		if (configMap != null) {
			String delimiter = " ";
			if (configMap.containsKey("separator")) {
				delimiter = (String) configMap.get("separator");
			}
			String fields = (String) configMap.get("fields");
			String[] props = fields.split(",");
			for (int i = 0; i < props.length; i++) {
				String prop = props[i];
				if (!result.equals("")) {
					result += delimiter;
				}
				Object val = widget.getModelByPath(prop, obj);
				if (val != null) {
					result += val;
				}
			}
		}
		return result;
	}

	private static Object listToString(Object obj, Map<String, Object> configMap) {
		List<Object> listStrs = com.ashera.widget.PluginInvoker.getList(obj);
		String result = "";
		String delimiter = ", ";
		if (configMap != null) {
			if (configMap.containsKey("separator")) {
				delimiter = (String) configMap.get("separator");
			}
		}
		if (listStrs != null) {
			for (Object listStr : listStrs) {
				if (!result.equals("")) {
					result += delimiter;
				}
				Object val = com.ashera.widget.PluginInvoker.getString(listStr);
				if (val != null) {
					result += val;
				}
			}
		}
		return result;
	}

	private static Object not(Object obj) {
		if (obj instanceof Boolean) {
			return !((Boolean) obj);
		}
		
		if (com.ashera.widget.PluginInvoker.isBoolean(obj)) {
			return !com.ashera.widget.PluginInvoker.getBoolean(obj);
		}
		return false;
	}

	private static Object total(IWidget widget, Object obj, Map<String, Object> configMap) {
		List<Object> objList = com.ashera.widget.PluginInvoker.getList(obj);
		
		float sum = 0;
		if (objList != null) {
			for (Object object : objList) {
				sum += com.ashera.widget.PluginInvoker.getFloat(widget.getModelByPath((String) configMap.get("path"), object));
			}
		}
		return sum;
	}

	private static String getDelimiter(Map<String, Object> configMap) {
		String delimiter = ", ";
		if (configMap.containsKey("separator")) {
			delimiter = (String) configMap.get("separator");
		}
		return delimiter;
	}

	private static Map<String, Object> getMap(Object obj) {
		if (obj instanceof Map) {
			return (Map<String, Object>) obj;
		}
		if (obj instanceof IFlatMap) {
			return ((IFlatMap) obj).getHierarchicalMap();
		}
		return null;
	}

	public static boolean isVisible(Object obj) {
		if (obj instanceof java.util.List) {
			return ((java.util.List) obj).size() > 0;
		}
		if (obj instanceof String) {
			return !((String) obj).isEmpty();
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if (com.ashera.widget.PluginInvoker.isBoolean(obj)) {
			return com.ashera.widget.PluginInvoker.getBoolean(obj);
		}
		return obj != null;
	}


	private static Object multiply(IWidget w, Object obj, Map<String, Object> configMap) {
		float val1 = 0;
		float val2 = 0;
		if (configMap.containsKey("op1")) {
			val1 = com.ashera.widget.PluginInvoker.getFloat(w.getModelByPath((String) configMap.get("op1"), obj));
		}
		
		if (configMap.containsKey("op2")) {
			val2 = com.ashera.widget.PluginInvoker.getFloat(w.getModelByPath((String) configMap.get("op2"), obj));
		}
		return val1 * val2;
	}
	

	private static Object getFileAsset(Object obj, IWidget widget, Map<String, Object> configMap) {
		if (obj == null || obj.equals("")) {
			return null;
		}
		return com.ashera.widget.PluginInvoker.getFileAsset((String) obj, widget.getFragment());
	}
}
