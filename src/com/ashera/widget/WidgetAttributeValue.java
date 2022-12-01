package com.ashera.widget;

public class WidgetAttributeValue {
	private String value;
	private String orientation = "default";
	private String minWidth;
	private String minHeight;
	private String maxWidth;
	private String maxHeight;
	private WidgetAttributeValueFilter filter = new WidgetAttributeValueFilter() {
		
		@Override
		public String filter(String value) {
			return value;
		}
	};

	public WidgetAttributeValueFilter getFilter() {
		return filter;
	}
	public void setFilter(WidgetAttributeValueFilter filter) {
		this.filter = filter;
	}
	public String getValue() {
		return filter.filter(value);
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getMinWidth() {
		return minWidth;
	}
	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}
	public String getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(String minHeight) {
		this.minHeight = minHeight;
	}
	public String getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}
	public String getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public WidgetAttributeValue(String value) {
		super();
		this.value = value;
	}
	
	public WidgetAttributeValue(String value, String orientation, String minWidth,
			String minHeight, String maxWidth, String maxHeight) {
		super();
		this.value = value;
		this.orientation = orientation;
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}
	
	@Override
	public String toString() {
		return value + " o:" + orientation + " mw:" + minWidth + " mh:" + minHeight
				+ " mxw:" + maxWidth + " mxh:" + maxHeight;
	}
}
