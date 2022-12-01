package com.ashera.converter;

import java.util.Map;

import com.ashera.converter.IConverter;
import com.ashera.core.IFragment;

public interface Converter { 
	Object convertFrom(IConverter converter, Map<String, Object> dependentAttributesMap, Object value, IFragment fragment);
	Object convertTo(IConverter converter, Object objValue, IFragment fragment);
	IConverter getConverter(String name);
	java.util.List<String> getDependentAttributes(IConverter converter);
	Object getColor(String color);
	float convertDpToPixel(String dimen);
	float convertSpToPixel(String dimen);
	String convertPixelToDp(Object dimen, boolean isInt);
	String convertPixelToSp(Object dimen, boolean isInt);
}
