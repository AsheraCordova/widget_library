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
package com.ashera.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ashera.core.IFragment;

public class WidgetFactory {	
	private static Map<String, TreeMap<String, WidgetAttribute>> attributeMap = new HashMap<>();
	private static Map<String, Set<WidgetAttribute>> styleAttributes = new HashMap<>();
	private static Map<String, TreeMap<String, WidgetAttribute>> constructorAttributeMap = new HashMap<>();
	private static Map<String, IWidget> registrationMap = new HashMap<String, IWidget>();
	private static Map<String, IDecorator> decoratorMap = new HashMap<String, IDecorator>();
	private static Map<String, ILifeCycleDecorator> lifeCycleDecoratorMap = new HashMap<>();
	private static Map<String, IBehavior> behaviorMap = new HashMap<String, IBehavior>();
	private static Map<String, List<IAttributable>> attributableForMap = new HashMap<>();
	private static Map<String, IAttributable> attributableMap = new HashMap<>();
	private static ICompositeDecorator compositeDecorator;
	
	public static IWidget createWidget(String localName, String themeName, HasWidgets parent, boolean addNativeOnly) {
		IWidget widget = WidgetFactory.get(localName, false);
		widget.setParent(parent);
		Map<String, Object> params = new HashMap<String, Object>();
		widget.applyThemeConstructorArgsStyle(themeName, params);
		widget.create(parent.getFragment(), params);
		widget.applyThemeStyle(themeName);
		if (addNativeOnly) {
			parent.add(widget, -2);
		} else {
			parent.add(widget, -1);
		}
		widget.initialized();
		return widget;
	}
	
	public static Set<String> getConstructorAttributes(String localname) {
		TreeMap<String, WidgetAttribute> treeMap = constructorAttributeMap.get(localname);
		
		if (treeMap == null) {
			return null;
		}
		return treeMap.keySet();
	}
	public static void registerConstructorAttribute(String localname, WidgetAttribute.Builder builder) {
		WidgetAttribute widgetAttribute = builder.build();
		if (!constructorAttributeMap.containsKey(localname)) {
			constructorAttributeMap.put(localname, new TreeMap<String, WidgetAttribute>());
		}
		String attributeName = widgetAttribute.getAttributeName();
		if (!constructorAttributeMap.get(localname).containsKey(attributeName)) {
			constructorAttributeMap.get(localname).put(attributeName, widgetAttribute);
		}
	}

	public static ICompositeDecorator getCompositeDecorator() {
		return compositeDecorator;
	}

	public static void register(ICompositeDecorator compositeDecorator) {
		WidgetFactory.compositeDecorator = compositeDecorator;
	}
	
	public static IAttributable getAttributable(String localname) {
		return attributableMap.get(localname);
	}

	public static List<IAttributable> getAttributables(String... localnames) {
		List<IAttributable> attributables = new ArrayList<IAttributable>();
		for (String localname : localnames) {
			List<IAttributable> list = attributableForMap.get(localname);
			if (list != null) {
				attributables.addAll(list);
			}
			
		}
		return attributables;
	}

	public static IWidget get(String localname, boolean isLazy) {
		IWidget widgetPrototype = registrationMap.get(localname);
		//temp fix to be used for testing
		//if (widgetPrototype == null && !localname.equals("data")) {
		//widgetPrototype = registrationMap.get("View");
		//}
		if (widgetPrototype != null) {
			if (isLazy) {
				IWidget newLazyInstance = widgetPrototype.newLazyInstance();
				if (!WidgetFactory.isAttributeLoaded(localname)) {
					newLazyInstance.loadAttributes(localname);
					widgetPrototype.loadAttributes(localname);
				}
				return newLazyInstance;
			}
			IWidget newInstance = widgetPrototype.newInstance();
			if (!WidgetFactory.isAttributeLoaded(localname)) {
				newInstance.loadAttributes(localname);
			}
			return newInstance;
		} 
		
		return null;
	}
	
	public static IDecorator getDecorator(ICompositeDecorator wrappingWidget, IWidget wrappedWidget, String localname) {
		IDecorator widgetPrototype = decoratorMap.get(localname);
		
		if (widgetPrototype != null) {			
			if (!WidgetFactory.isAttributeLoaded(wrappingWidget.getLocalName())) {
				wrappingWidget.loadAttributes(wrappingWidget.getLocalName());
			}
			return widgetPrototype.newInstance(wrappingWidget, wrappedWidget);
		} 
		
		return null;
	}
	
	public static void loadWidget(IWidget widget, WidgetAttributeMap attributes, IFragment fragment, Map<String, Object> params) {
		widget.create(fragment, params);
		
		if (attributes != null) {
			widget.updateWidgetMap(attributes);
		}
		widget.initialized();
	}
	
	public static void register(IWidget ui) {
		registrationMap.put(ui.getLocalName(), ui);
	}
	
	public static void registerDecorator(IDecorator ui) {
		decoratorMap.put(ui.getLocalName(), ui);
	}

	public static boolean isAttributeLoaded(String localname) {
		return attributeMap.containsKey(localname);
	}
	
	public static void registerAttribute(String localname, WidgetAttribute.Builder builder) {
		registerAttribute(localname, builder.build());
	}
	
	public static void registerAttribute(String localname, WidgetAttribute widgetAttribute) {
		if (widgetAttribute.getStylePriority() != null) {
			updateStyleAttrs(localname, widgetAttribute);
		}
		if (!attributeMap.containsKey(localname)) {
			attributeMap.put(localname, new TreeMap<String, WidgetAttribute>());
		}
		String attributeName = widgetAttribute.getAttributeName();
		if (!attributeMap.get(localname).containsKey(attributeName)) {
			attributeMap.get(localname).put(attributeName, widgetAttribute);
		}
	}

	private static void updateStyleAttrs(String localname, WidgetAttribute widgetAttribute) {
		if (!styleAttributes.containsKey(localname)) {
			styleAttributes.put(localname, new java.util.TreeSet<WidgetAttribute>(new java.util.Comparator<WidgetAttribute>() {
				@Override
				public int compare(WidgetAttribute o1, WidgetAttribute o2) {
					if (o1.getAttributeName().equals(o2.getAttributeName())) {
						return 0;
					}
					
					return o2.getStylePriority() - o1.getStylePriority();
				}
			}));
		}
		styleAttributes.get(localname).add(widgetAttribute);
	}
	
	public static Set<WidgetAttribute> getStyleAttributes(String localname) {
		return styleAttributes.get(localname);
	}

	public static WidgetAttribute getAttribute(String localname, String attributeName) {	
		TreeMap<String, WidgetAttribute> treeMap = attributeMap.get(localname);
		if (treeMap != null) {
			return treeMap.get(attributeName);
		}
		
		return null;
	}
	
	public static ILifeCycleDecorator getLifeCycleDecor(String lifeCycleDecorator, IWidget widget) {
		ILifeCycleDecorator decorator = lifeCycleDecoratorMap.get(lifeCycleDecorator);
		
		if (decorator != null) {
			return decorator.newInstance(widget);
		} 
		
		return null;
	}

	public static void registerLifeCycleDecorator(String decorator, ILifeCycleDecorator lifeCycleDecorator) {
		lifeCycleDecoratorMap.put(decorator, lifeCycleDecorator);
	}
	
	public static void registerBehavior(String decorator, IBehavior behavior) {
		behaviorMap.put(decorator, behavior);
	}
	
	public static Object getBehavior(String behavior) {
		IBehavior decorator = behaviorMap.get(behavior);
		
		if (decorator != null) {
			return decorator.newInstance();
		} 
		
		return null;
	}

	public static void registerAttributableFor(String localName, IAttributable attributable) {
		if (!attributableForMap.containsKey(localName)) {
			attributableForMap.put(localName, new ArrayList<>());
		}
		
		attributableMap.put(attributable.getLocalName(), attributable);
		attributableForMap.get(localName).add(attributable);
	}

	public static java.util.Collection<WidgetAttribute> getAttributes(String localName) {
		return attributeMap.get(localName).values();
	}
}
