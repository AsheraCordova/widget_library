package com.ashera.widget;

import java.util.List;

import com.ashera.model.IModelGroup;
import com.ashera.model.LoopParam;

public interface HasWidgets extends IWidget, IModelGroup {
    // remove/add the delete widgets
	void add(IWidget w, int index);
	void clear();
	IWidget get(int index);
	List<IWidget> getWidgets();
	java.util.Iterator<IWidget> iterator();
	boolean remove(IWidget w);
	boolean remove(int index);
	
	// template for repeating the widgets
	void addTemplate(Object objValue);
	   
    //  load lazy widgets - template widget to be loaded dynamically
    Object getChildAttribute(IWidget w, WidgetAttribute key);
	void setChildAttribute(IWidget widget, WidgetAttribute key, String strValue, Object objValue);
	
    HasWidgets getCompositeLeaf();
    boolean areWidgetItemsRecycled();
}
