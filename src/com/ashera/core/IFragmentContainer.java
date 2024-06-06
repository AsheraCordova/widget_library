package com.ashera.core;

import com.ashera.widget.IWidget;

public interface IFragmentContainer {

	void addOrReplaceFragment(String name, boolean add, String layout, String navGraphId, String tag);

	IWidget getActiveRootWidget();

}
