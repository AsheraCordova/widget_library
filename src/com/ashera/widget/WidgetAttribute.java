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

public class WidgetAttribute {	
	private String attributeName;
	private String attributeType;
	private String arrayType;
	private String arrayListToFinalType;
	private int order;
	private boolean isForChild;
	private String decorator;
	private int bufferStrategy;
	private int updateUiFlag;
	private boolean applyBeforeChildAdd;
	private Integer stylePriority;
	private int simpleWrapableViewStrategy = IWidget.APPLY_TO_VIEW_WRAPPER;

	public Integer getStylePriority() {
		return stylePriority;
	}

	private WidgetAttribute(Builder builder) {
		this.attributeName = builder.attributeName;
		this.attributeType = builder.attributeType;
		this.order = builder.order;
		this.isForChild = builder.isForChild;
		this.decorator = builder.decorator;
		this.bufferStrategy = builder.bufferStrategy;
		this.updateUiFlag = builder.updateUiFlag;
		this.applyBeforeChildAdd = builder.applyBeforeChildAdd;
		this.arrayType = builder.arrayType;
		this.arrayListToFinalType = builder.arrayListToFinalType;
		this.simpleWrapableViewStrategy = builder.simpleWrapableViewStrategy;
		this.stylePriority = builder.stylePriority;
	}

	public String getArrayListToFinalType() {
		return arrayListToFinalType;
	}

	public void setArrayListToFinalType(String arrayListToFinalType) {
		this.arrayListToFinalType = arrayListToFinalType;
	}

	public boolean isApplyBeforeChildAdd() {
		return applyBeforeChildAdd;
	}

	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return attributeName.equalsIgnoreCase((String) obj);
		}
		return attributeName.equals(((WidgetAttribute) obj).attributeName);
	}
	
	public int hashCode() {
		return attributeName.hashCode();
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	

	public boolean isForChild() {
		return isForChild;
	}

	public void setForChild(boolean isForChild) {
		this.isForChild = isForChild;
	}

	public String getDecorator() {
		return decorator;
	}

	public void setDecorator(String decorator) {
		this.decorator = decorator;
	}

	public int getBufferStrategy() {
		return bufferStrategy;
	}

	public void setBufferStrategy(int bufferStrategy) {
		this.bufferStrategy = bufferStrategy;
	}

	public int getUpdateUiFlag() {
		return updateUiFlag;
	}

	public void setUpdateUiFlag(int updateUiFlag) {
		this.updateUiFlag = updateUiFlag;
	}
	
	@Override
	public String toString() {
		return attributeName;
	}

	public String getArrayType() {
		return arrayType;
	}

	public void setArrayType(String arrayType) {
		this.arrayType = arrayType;
	}
	
	/**
	 * Creates builder to build {@link WidgetAttribute}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}


	public int getSimpleWrapableViewStrategy() {
		return simpleWrapableViewStrategy;
	}

	public void setSimpleWrapableViewStrategy(int simpleWrapableViewStrategy) {
		this.simpleWrapableViewStrategy = simpleWrapableViewStrategy;
	}
	
	/**
	 * Builder to build {@link WidgetAttribute}.
	 */
	public static final class Builder {
		private String attributeName;
		private String attributeType;
		private int order;
		private int simpleWrapableViewStrategy;
		private boolean isForChild;
		private String decorator;
		private int bufferStrategy;
		private int updateUiFlag;
		private boolean applyBeforeChildAdd;
		private String arrayType;
		private String arrayListToFinalType;
		private Integer stylePriority;
		public Builder() {
		}

		public Builder withName(String attributeName) {
			this.attributeName = attributeName;
			return this;
		}

		public Builder withType(String attributeType) {
			this.attributeType = attributeType;
			return this;
		}
		
		public Builder withArrayType(String arrayType) {
			this.arrayType = arrayType;
			return this;
		}
		
		public Builder withArrayListToFinalType(String arrayListToFinalType) {
			this.arrayListToFinalType = arrayListToFinalType;
			return this;
		}

		public Builder withOrder(int order) {
			this.order = order;
			return this;
		}

		public Builder withStylePriority(Integer stylePriority) {
			this.stylePriority = stylePriority;
			return this;
		}
		public Builder forChild() {
			this.isForChild = true;
			return this;
		}

		public Builder withDecorator(String decorator) {
			this.decorator = decorator;
			return this;
		}

		public Builder withBufferStrategy(int bufferStrategy) {
			this.bufferStrategy = bufferStrategy;
			return this;
		}
		
		public Builder withSimpleWrapableViewStrategy(int simpleWrapableViewStrategy) {
			this.simpleWrapableViewStrategy = simpleWrapableViewStrategy;
			return this;
		}

		public Builder withUiFlag(int updateUiFlag) {
			this.updateUiFlag = updateUiFlag;
			return this;
		}

		public Builder beforeChildAdd() {
			this.applyBeforeChildAdd = true;
			return this;
		}

		public WidgetAttribute build() {
			return new WidgetAttribute(this);
		}
	}
}
