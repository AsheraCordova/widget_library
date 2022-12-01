package com.ashera.converter;

import java.util.List;
import java.util.Map;

import com.ashera.core.IFragment;

public interface IConverter<T, V> {
	T convertFrom(V value, Map<String, Object> dependentAttributesMap, IFragment fragment);
	V convertTo(T value, IFragment fragment);
	List<String> getDependentAttributes();
}
