package com.ashera.widget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class WidgetAttributeMap extends TreeMap<WidgetAttribute, List<WidgetAttributeValue>> {
	private WidgetAttributeMapFilter filter;
	private WidgetAttributeMap parent;
	private java.util.HashMap<String, com.ashera.widget.WidgetAttribute> keyMap = new java.util.HashMap<>();
	public WidgetAttributeMap getParent() {
		return parent;
	}
	public void setParent(WidgetAttributeMap parent) {
		this.parent = parent;
	}
	public WidgetAttributeMapFilter getFilter() {
		return filter;
	}
	public void setFilter(WidgetAttributeMapFilter filter) {
		this.filter = filter;
	}
	
	private HashMap<String, Integer> orderMap;
	public WidgetAttributeMap() {
		this(new HashMap<String, Integer>());
	}
	public WidgetAttributeMap(final HashMap<String, Integer> orderMap) {
		super(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
			
				if (o1 instanceof WidgetAttribute && o2 instanceof WidgetAttribute) {
					String key1 = ((WidgetAttribute)o1).getOrder() + ((WidgetAttribute)o1).getAttributeName().toLowerCase();
					String key2 = ((WidgetAttribute)o2).getOrder() + ((WidgetAttribute)o2).getAttributeName().toLowerCase();

					return key1.compareTo(key2);
				}
				
				if (o1 instanceof String && o2 instanceof WidgetAttribute) {
					String key1 = orderMap.get(((String)o1).toLowerCase()) + ((String)o1).toLowerCase();
					String key2 = ((WidgetAttribute)o2).getOrder() + ((WidgetAttribute)o2).getAttributeName().toLowerCase();

					return key1.compareTo(key2);
				}
				
				if (o1 instanceof WidgetAttribute && o2 instanceof String) {
					String key1 = ((WidgetAttribute)o1).getOrder() + ((WidgetAttribute)o1).getAttributeName().toLowerCase();
					String key2 = orderMap.get(((String)o2).toLowerCase()) + ((String)o2).toLowerCase();

					return key1.compareTo(key2);
				}
				return 0;
			}
		});
		this.orderMap = orderMap;
	}
	
	@Override
	public List<WidgetAttributeValue> put(WidgetAttribute key, List<WidgetAttributeValue> value) {
		if (key == null) {
			return null;
		}
		keyMap.put(key.getAttributeName(), key);
		orderMap.put(key.getAttributeName().toLowerCase(), key.getOrder());
		return super.put(key, value);
	}
	
	public void put(WidgetAttribute key, WidgetAttributeValue value) {
		if (key != null) {
			List<WidgetAttributeValue> values = get(key);
			if (values == null) {
				put(key, new ArrayList<WidgetAttributeValue>());
			}
			get(key).add(value);
		}
	}

	public WidgetAttribute getWidgetAttribute(String key) {
		return keyMap.get(key);
	}
}
