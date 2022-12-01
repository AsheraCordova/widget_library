package com.ashera.widget;

public interface IWrapableWidget {
	Object createWrapperViewHolder(int viewtype);
	Object createWrapperView(Object wrapperParent, int viewtype);
	void addForegroundIfNeeded();
	Object getForeground();
}