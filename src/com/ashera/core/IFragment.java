package com.ashera.core;

import java.util.List;

import com.ashera.css.StyleSheet;
import com.ashera.widget.IWidget;
import com.ashera.widget.bus.EventBus;

public interface IFragment {
	String getFragmentId();
	String getActionUrl();
	
	// lifecycle methods
	void onAttach(IActivity activity);
	void onDetach();
	void onCreate();
	void onDestroy();
	void onResume();
	void onPause();
	void onCloseDialog();
	Object onCreateView(boolean measure);
	IFragment getRootFragment();
	
	EventBus getEventBus();
	IWidget getRootWidget();
	boolean hasDevData(String key);
	Object getDevData(String key);
	IActivity getRootActivity();
	Object getArgumentsBundle();
	void setRootWidget(IWidget widget);
	void storeUserData(String varName, Object objValue);
	Object getUserData(String varName);
	
	void storeInTempCache(String varName, Object objValue);
	Object getFromTempCache(String varName);
	Object getParentForRootWidget();
	
	StyleSheet getStyleSheet();
	void setStyleSheet(StyleSheet styleSheet);
	boolean isViewLoaded();
	
	void setFrame(int x, int y, int width, int height);
	void remeasure();
	void disableRemeasure();
	void enableRemeasure();

	List<Object> getDisposables();
	void addDisposable(Object disposable);
	
	void addListener(IWidget widget, Object listener);
	<T> List<T> getListener(Class<T> type);
	<T> List<T> getListener(IWidget widget, Class<T> type);
	void removeListener(IWidget widget, Object listener);
	
	void addError(com.ashera.model.Error error);
	boolean hasErrors();
	void resizeWindow(int width, int height);
	
	String getInlineResource(String key);
	void setInlineResource(String key, String value, boolean append);
	IFragment getParent();
	String getUId();
}
