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

import java.util.List;

public class SimpleWrapableView {
	@com.google.j2objc.annotations.Weak  Object wrappedView;
	private Object foreground;
	@com.google.j2objc.annotations.Weak private Object wrapperViewHolder;
	private boolean disableForeground;
	private int viewtype;

	public int getViewtype() {
		return viewtype;
	}

	public boolean isDisableForeground() {
		return disableForeground;
	}

	public void setDisableForeground(boolean disableForeground) {
		this.disableForeground = disableForeground;
	}

	public SimpleWrapableView(IWidget widget, int viewtype) {
		this.viewtype = viewtype;
		if (viewtype != -1) {
			wrapperViewHolder = widget.createWrapperViewHolder(viewtype);
		} else {
			wrapperViewHolder = widget.getParent().asNativeWidget();
		}

		wrappedView = widget.createWrapperView(wrapperViewHolder, viewtype);
	}

	public List<Object> getNativeWidgets() {
		if (viewtype == -1) {
			return java.util.Arrays.asList(wrappedView);
		}
		
		if (foreground != null) {
			return java.util.Arrays.asList(wrapperViewHolder, wrappedView, foreground);
		}
		return java.util.Arrays.asList(wrapperViewHolder, wrappedView);
	}

	public Object asNativeWidget() {
		if (viewtype == -1) {
			return wrappedView;
		}

		return wrapperViewHolder;
	}

	public void setWrappedView(Object wrappedView) {
		this.wrappedView = wrappedView;
	}

	public Object getWrappedView() {
		return wrappedView;
	}

	public Object getWrapperViewHolder() {
		return wrapperViewHolder;
	}


	public boolean isViewWrapped() {
		return viewtype != -1;
	}

	public Object getForeground() {
		return foreground;
	}

	public void setForeground(Object foreground) {
		this.foreground = foreground;
	}
}
