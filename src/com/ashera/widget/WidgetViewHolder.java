package com.ashera.widget;

import java.util.List;

import com.ashera.model.ModelExpressionParser.ModelPojoToUiHolder;

/**
 * View holder pattern for widgets like listview, recycleview. 
 */
public class WidgetViewHolder {
	// widget which will be lazily loaded using loadLazyWidget
	public IWidget widget;
	public List<AttributeViewHolder> attributes = new java.util.ArrayList<>();
	
	public static class AttributeViewHolder {
		// apply setAttribute on this widget for this attribute and model 
		public IWidget childWidget;
		public ModelPojoToUiHolder modelPojoToUiHolder;
		public WidgetAttribute widgetAttribute;
	}

}
