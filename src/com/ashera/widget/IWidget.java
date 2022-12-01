package com.ashera.widget;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ashera.core.IFragment;
import com.ashera.model.IModel;
import com.ashera.model.LoopParam;

public interface IWidget extends IModel, HasLifeCycleDecorators, IWrapableWidget {
    final static int COMMAND_EXEC_GETTER_METHOD = 0x1;
    final static int COMMAND_EXEC_SETTER_METHOD = 0x2;
    final static int BUFFER_STRATEGY_NONE = 0x0;
    final static int BUFFER_STRATEGY_DURING_INIT = 0x1;
    final static int BUFFER_STRATEGY_ALWAYS = 0x2;
    
    final static int UPDATE_UI_NONE = 0x0;
    final static int UPDATE_UI_REQUEST_LAYOUT = 0x1;
    final static int UPDATE_UI_INVALIDATE = 0x2;
    final static int UPDATE_UI_REQUEST_LAYOUT_N_INVALIDATE = UPDATE_UI_INVALIDATE | UPDATE_UI_REQUEST_LAYOUT;

    final static int APPLY_TO_VIEW_WRAPPER = 0x1;
    final static int APPLY_TO_VIEW_HOLDER = 0x2;
    final static int APPLY_TO_FOREGROUND = 0x4;

    final static int APPLY_TO_ALL = APPLY_TO_VIEW_HOLDER | APPLY_TO_VIEW_WRAPPER | APPLY_TO_FOREGROUND;
    
	interface CommandCallBack {
		void onWidget(IWidget widget);
	}
	// widget methods
	Object asWidget();
	Object asNativeWidget();	
	String getLocalName();
	String getGroupName();
	Object unwrap(Object widget);
	
	// life cycle methods
	IWidget newInstance();
	IWidget newLazyInstance();
	void create(IFragment fragment, Map<String, Object> params);	
	void initialized();
	
	// data accessor method
	Map<String, Object> getParams();
	void setParams(Map<String, Object> params);
	Object getUserData(String key);
	void storeUserData(String key, Object object);
	void storeInTempCache(String varName, Object objValue);
	Object getFromTempCache(String varName);

	// special accessor for id
	String getId();
	int getIdAsInt();
	void setId(String id);
	String getBehaviorGroupId();
	void setBehaviorGroupId(String id);
	String getComponentId();
	void setComponentId(String componentId);
	
	// TODO: REVIEW attribute methods
	String getAttributeValue(String attr);
	WidgetAttributeMap getAttributes();
	void loadAttributes(String localName);
	/*
	 * Method set Attribute on widget if convert method has already been called.. 
	 */
	void setAttribute(WidgetAttribute widgetAttribute, Object objValue, boolean skipConvert);
	
	// TODO - this method should never be called from outside may be can hide this
	void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator);
	Object getAttribute(WidgetAttribute key, ILifeCycleDecorator decorator);
	WidgetAttribute getAttribute(final HasWidgets parent, final String localName, String key);
	void updateWidgetMap(WidgetAttribute key, WidgetAttributeValue value);
	void updateWidgetMap(WidgetAttributeMap map);
	
	HasWidgets getParent();
	void setParent(HasWidgets widget);

	// find widgets
	IWidget findWidgetById(String id);
	
	// lifecycle methods
	IWidgetLifeCycleListener getListener();
	void setListener(IWidgetLifeCycleListener listener); 
	
	// execute list of commands..
	void executeCommand(Map<String, Object> payLoad, CommandCallBack command, int methods);
	void executeCommand(IWidget rootWidget, List<Object> payLoad, CommandCallBack command, int methods);
    void setVisible(boolean visible);
    int getBaseLine();
	boolean isInitialised();
	IWidget findNearestView(String id);
	IFragment getFragment();
	
	//
	void afterParentInit();
	boolean isAfterParentInitRequired();

	void requestLayout();
	void invalidate();
	boolean isInvalidateOnFrameChange();
	void setInvalidateOnFrameChange(boolean invalidateOnFrameChange);
	
	void applyAttributeCommand(String sourceName, String commandName, String[] attributes, boolean add, Object... args);
	void rerunCommandOnSource(String sourceName, String phase);
	void reapplyAttributeCommand(String sourceName, String commandName, String phase, Object... args);
	Object getAttributeCommandValue(String sourceName, String commandName, String attributeName);
	AttributeCommandChain getAttributeCommandChain(String sourceName);
	void registerForAttributeCommandChain(String... attributes);
	void registerForAttributeCommandChainWithPhase(String phase, String... attributes);
	public void runAttributeCommands(Object nativeWidget, String phase, String commandFilterRegex, Object... args);
	void replayBufferedEvents();
	Object quickConvert(Object value, String type);
	Object quickConvert(Object objValue, String type, String arrayType, String finalArrayType);
	
	void drawableStateChanged();
	void setDrawableBounds(int l, int t, int r, int b);
	
	int getZIndex();
	void setZIndex(int zIndex);
	void setEventBubblers(Collection<Integer> flags);
	Set<Integer> getEventBubblers();
	
	void applyThemeConstructorArgsStyle(String themeName, Map<String, Object> params);
	void applyThemeStyle(String themeName);
	
	IWidget loadLazyWidgets(HasWidgets parent, int index, String idKey, LoopParam model);
	IWidget loadLazyWidgets(LoopParam model);
    IWidget loadLazyWidgets(HasWidgets hasWidgets);
}
