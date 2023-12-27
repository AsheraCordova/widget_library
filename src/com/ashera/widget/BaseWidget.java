package com.ashera.widget;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ashera.converter.ConverterFactory;
import com.ashera.converter.IConverter;
import com.ashera.core.IFragment;
import com.ashera.model.ExpressionMethodHandler;
import com.ashera.model.IFlatMap;
import com.ashera.model.LoopParam;
import com.ashera.model.ModelDataType;
import com.ashera.model.ModelExpressionParser;
import com.ashera.model.ModelExpressionParser.ModelEventHolder;
import com.ashera.model.ModelExpressionParser.ModelPojoToUiHolder;
import com.ashera.model.ModelExpressionParser.ModelStoreVarHolder;
import com.ashera.model.ModelExpressionParser.ModelUiToPojoHolder;
import com.ashera.model.ModelExpressionParser.ModelVarHolder;
import com.ashera.model.ModelScope;
import com.ashera.model.ModelStore;
import com.ashera.model.PlainMapException;
import com.ashera.widget.bus.EventBus;
import com.ashera.widget.bus.EventBusHandler;

public abstract class BaseWidget implements IWidget {
	@com.google.j2objc.annotations.WeakOuter
    public class LazyBaseWidget extends BaseWidget {
		private static final String LOCAL_NAME = "LazyBaseWidget";
		public LazyBaseWidget() {
			super(LOCAL_NAME, LOCAL_NAME);
		}

		@Override
		public IWidget newInstance() {
			return WidgetFactory.get(BaseWidget.this.getLocalName(), false);
		}


		@Override
		public Object asWidget() {
			return this;
		}

		@Override
		public Object asNativeWidget() {
			return null;
		}

		@Override
		public void create(IFragment fragment, Map<String, Object> params) {
			super.create(fragment, params);
		}

		@Override
		public void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator) {
		}
		
		
		@Override
		public void initialized() {
		}

		@Override
		public String getLocalName() {
			return BaseWidget.this.getLocalName();
		}

		@Override
		public void loadAttributes(String localName) {
		}


		@Override
		public Object getAttribute(WidgetAttribute key, ILifeCycleDecorator decorator) {
			return null;
		}
		
		@Override
		public String getGroupName() {
			return BaseWidget.this.getGroupName();
		}

		@Override
		public Class getViewClass() {
			return BaseWidget.this.getViewClass();
		}
	}


	protected @com.google.j2objc.annotations.Weak HasWidgets parent;
	protected WidgetAttributeMap attributes = new WidgetAttributeMap();
	protected Map<String, Object> params;
	protected Map<String, Object> userData;
	protected Map<String, Object> tmpCache;
	private IWidgetLifeCycleListener listener;
	protected String localName;
	protected String groupName;
	private @com.google.j2objc.annotations.Weak EventBus eventBus;
	private Map<String, ILifeCycleDecorator> cachedDecorators;
	private String id;
	private String behaviorGroupId;
    private List<EventBusHandler> eventBusHandlers;
    private boolean initialised;
    protected @com.google.j2objc.annotations.Weak IFragment fragment;

	private Map<String, AttributeCommandChain> attributeCommandChainMap;
	private Map<String, List<String>> updateAttributes;
	private Map<String, AttributeCommand> attributeCommandMap;
	private Map<String, Set<String>> commandPhases;
	private List<EventHolder> bufferedAttributes;
	protected java.util.Map<String, List<ILifeCycleDecorator>> methodListeners;
	private boolean onMethodCalled;
	private int zIndex;
	private Set<Integer> eventBubblers;
	private String componentId;
	private Object animator;
	
	@Override
	public Object getAnimator() {
		return animator;
	}

	@Override
	public void setAnimator(Object animator) {
		this.animator = animator;
	}

	@Override
	public int getZIndex() {
		return zIndex;
	}

	@Override
	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	class EventHolder {
		WidgetAttribute widgetAttribute;
		IWidget childWidget;
		Object objValue;
		
		public EventHolder(WidgetAttribute widgetAttribute, IWidget childWidget, Object objValue) {
			this.widgetAttribute = widgetAttribute;
			this.objValue = objValue;
			this.childWidget = childWidget;
		}

	}
	
	@Override
	public void replayBufferedEvents() {
		if (bufferedAttributes != null) {
			replayBufferedEventsInternal();
			bufferedAttributes.clear();
			bufferedAttributes = null;
		}
	}

	protected void replayBufferedEventsInternal() {
		if (bufferedAttributes != null) {
			for (EventHolder eventHolder : bufferedAttributes) {
				applyStyleToWidgetWithoutBuffering(eventHolder.widgetAttribute, eventHolder.childWidget, eventHolder.objValue, null, false);
			}
		}
	}
	
	public boolean hasBufferedAttributes() {
		return bufferedAttributes != null && bufferedAttributes.size() > 0;
	}

	
	@Override
	public void registerForAttributeCommandChainWithPhase(String phase, String... attrs) {
		if (commandPhases == null) {
			commandPhases = new HashMap<>();
		}
		Set<String> phases = commandPhases.get(phase);
		if (phases == null) {
			phases = new java.util.LinkedHashSet<>();
			commandPhases.put(phase, phases);
		}
		phases.addAll(Arrays.asList(attrs));
		registerForAttributeCommandChain(attrs);
	}
	
	public void registerForAttributeCommandChain(String... attrs) {
		if (attributeCommandChainMap == null) {
			attributeCommandChainMap = new HashMap<>();
		}
		
		if (updateAttributes == null) {
			updateAttributes = new HashMap<>();
		}
		
		for (String attr : attrs) {
			AttributeCommandChain attributeCommandChain = attributeCommandChainMap.get(attr);
			if (attributeCommandChain == null) {
				attributeCommandChain = new AttributeCommandChain();
				attributeCommandChainMap.put(attr, attributeCommandChain);
			}
		}
	}
	
	@Override
  	public void applyAttributeCommand(String sourceName, String commandName, String[] attributes, boolean add, Object... args) {
		if (attributeCommandMap == null) {
			attributeCommandMap = new HashMap<>();
		}
		
		if (add) {
			AttributeCommand attributeCommand = getAttributeCommand(sourceName, commandName, args);
			addAttributeCommandToChain(sourceName, attributes, attributeCommand);
		} else {
			AttributeCommand attributeCommand = attributeCommandMap.get(sourceName + commandName);
			removeAttributeCommandFromChain(sourceName, attributes, attributeCommand);
		}
  	}
	
	@Override
	public void reapplyAttributeCommand(String sourceName, String commandName, String phase, Object... args) {
		AttributeCommand attributeCommand = getAttributeCommand(sourceName, commandName, args);
		rerunCommandOnSource(sourceName, phase);
		
	}

	@Override
	public void rerunCommandOnSource(String sourceName, String phase) {
		if (attributeCommandChainMap == null || !attributeCommandChainMap.containsKey(sourceName)) {
			throw new RuntimeException("Call registerValueCommandExecutor for attribute " + sourceName + " in nativeCreate method ");
		}
		AttributeCommandChain attributeCommandChain = getAttributeCommandChain(sourceName);
		Object value = attributeCommandChain.getOriginalStringValue();
		applyStyleToWidget(WidgetFactory.getAttribute(getLocalName(), sourceName), null, value, phase);
	}
	
	@Override
	public Object getAttributeCommandValue(String sourceName, String commandName, String attributeName) {
		if (attributeCommandMap != null) {
			AttributeCommand attributeCommand = attributeCommandMap.get(sourceName + commandName);
			if (attributeCommand != null) {
				return attributeCommand.getValue(attributeName);
			}
		}
		return null;
	}
	
	
	public AttributeCommand getAttributeCommand(String sourceName, String commandName, Object... args) {
		if (attributeCommandMap == null) {
			attributeCommandMap = new HashMap<>();
		}
		AttributeCommand attributeCommand = attributeCommandMap.get(sourceName + commandName);
		
		if (attributeCommand == null) {
			attributeCommand = ConverterFactory.getCommandConverter(commandName, args);
			attributeCommandMap.put(sourceName + commandName, attributeCommand);
		} else {
			attributeCommand.updateArgs(args);
		}
		
		return attributeCommand;
	}
	
	private void addAttributeCommandToChain(String sourceName, String[] attributes, AttributeCommand attributeCommand) {
		
		if (attributeCommandChainMap == null || !attributeCommandChainMap.containsKey(sourceName)) {
			throw new RuntimeException("Call registerValueCommandExecutor for attribute " + sourceName + " in nativeCreate method ");
		}
		AttributeCommandChain attributeCommandChain = getAttributeCommandChain(sourceName);
		attributeCommandChain.addCommand(attributeCommand);

		for (String attributeName : attributes) {
			if (!updateAttributes.containsKey(attributeName)) {
				updateAttributes.put(attributeName, new ArrayList<>());
			}
			updateAttributes.get(attributeName).add(sourceName);
		}

		if (isInitialised() && attributes.length > 0) {
			Object value = attributeCommandChain.getOriginalStringValue();
			applyStyleToWidget(WidgetFactory.getAttribute(getLocalName(), sourceName), value);
		}
	}

	@Override
	public AttributeCommandChain getAttributeCommandChain(String sourceName) {
		if (attributeCommandChainMap == null) {
			return null;
		}
		AttributeCommandChain attributeCommandChain = attributeCommandChainMap.get(sourceName);
		return attributeCommandChain;
	}
	
	public void removeAttributeCommandFromChain(String sourceName, String[] attributes, AttributeCommand attributeCommand) {
		if (attributeCommandChainMap != null) {
			AttributeCommandChain attributeCommandChain = attributeCommandChainMap.get(sourceName);
			attributeCommandChain.removeCommand(attributeCommand);
			
			for (String attributeName : attributes) {
				List<String> list = updateAttributes.get(attributeName);
				if (list != null) {
					list.remove(sourceName);
				}
			}
			
			if (isInitialised() && attributes.length > 0) {
				applyStyleToWidget(WidgetFactory.getAttribute(getLocalName(), sourceName), attributeCommandChain.getOriginalStringValue());
			}
		}
	}

    @Override
	public IFragment getFragment() {
		return fragment;
	}

	@Override
	public IWidget newLazyInstance() {
		return new LazyBaseWidget();
	}

	
	// widget methods
	@Override
	public Object unwrap(Object widget) {
		if (widget instanceof IWidget) {
			return ((IWidget) widget).asNativeWidget();
		}
		
		return widget;
	}
	
	// special accessor for id
	@Override
	public String getId() {
		return id;
	}
	public int getIdAsInt() {
		return (int) quickConvert(id, "id");
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getBehaviorGroupId() {
		return behaviorGroupId;
	}

	@Override
	public void setBehaviorGroupId(String behaviorGroupId) {
		this.behaviorGroupId = behaviorGroupId;
	}
	
	@Override
	public String getComponentId() {
		return componentId;
	}

	@Override
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	
	// life cycle methods
	@Override
	public void initialized() {
		applyStyleToWidgets();
		
		applyModelToWidget();
		
		if (cachedDecorators != null) {
			for (ILifeCycleDecorator decorator : cachedDecorators.values()) {
				decorator.initialized();
			}
		}
		
		initialised = true;
	}


	protected void applyStyleToWidgets() {
		Set<WidgetAttribute> widgetAttributes = attributes.keySet();
		for (WidgetAttribute widgetAttribute : widgetAttributes) {
			if (!widgetAttribute.isForChild()) {
				
				if (attributes.getFilter() == null || attributes.getFilter().accept(widgetAttribute.getAttributeName(), getLocalName())) {
					
					Object objValue = getAttributeValue(widgetAttribute.getAttributeName());
					
					if (objValue != null) {
						applyStyleToWidget(widgetAttribute, objValue);
					}
				}
			}
		}
	}

	@Override
	public Object quickConvert(Object objValue, String type) {
		IConverter converter = PluginInvoker.getConverter(type);

        Object convertedValue = objValue;

        if (converter != null) {
            Map<String, Object> dependentAttributesMap = getDependentAttributesMap(converter);
        	convertedValue = PluginInvoker.convertFrom(converter, dependentAttributesMap, objValue, fragment);
        }
        
		return convertedValue;
	}
	
	
	@Override
	public Object quickConvert(Object objValue, String type, String arrayType, String finalArrayType) {
		return handleArrayType(quickConvert(objValue, type), arrayType, finalArrayType); 
	}
	
	public void applyStyleToWidget(WidgetAttribute widgetAttribute, Object objValue) {
		applyStyleToWidget(widgetAttribute, null, objValue, null);
	}
	
	@Override
	public void setAttribute(String key, Object objValue, boolean skipConvert) {
		if (fragment.getRootWidget() == null) {
			return;
		}
		if (key.startsWith("layout_")) {
			WidgetAttribute widgetAttribute = WidgetFactory.getAttribute(getParent().getLocalName(), key);
			((BaseHasWidgets) getParent()).setChildAttribute(this, widgetAttribute, objValue, skipConvert);
		} else {
			WidgetAttribute attribute = WidgetFactory.getAttribute(localName, key);
			if (attribute != null) {
				setAttribute(attribute, objValue, skipConvert);
			}
		}
	}
	
	private void setAttribute(WidgetAttribute widgetAttribute, Object objValue, boolean skipConvert) {
		applyStyleToWidget(widgetAttribute, null, objValue, null, skipConvert);
		requestLayoutNInvalidateIfRequired(widgetAttribute.getUpdateUiFlag() );
	}
	
	
	private void applyStyleToWidget(WidgetAttribute widgetAttribute, IWidget childWidget, Object objValue, String phase) {
		applyStyleToWidget(widgetAttribute, childWidget, objValue, phase, false);
	}
	private void applyStyleToWidget(WidgetAttribute widgetAttribute, IWidget childWidget, Object objValue, String phase, boolean skipConvert) {
		int bufferStrategy = widgetAttribute.getBufferStrategy();
		if (bufferStrategy > 0 && ((bufferStrategy == BUFFER_STRATEGY_ALWAYS) || !(isInitialised() && bufferStrategy == BUFFER_STRATEGY_DURING_INIT))) {
			if (bufferedAttributes == null) {
				bufferedAttributes = new ArrayList<>();
			}
			bufferedAttributes.add(new EventHolder(widgetAttribute, childWidget, objValue));
			attributeBuffered();
			return;
		}
		applyStyleToWidgetWithoutBuffering(widgetAttribute, childWidget, objValue, phase, skipConvert);
	}

	protected void attributeBuffered() {
		
	}

	private void applyStyleToWidgetWithoutBuffering(WidgetAttribute widgetAttribute, IWidget childWidget,
			Object objValue, String phase, boolean skipConvert) {

		try {
            String type = widgetAttribute.getAttributeType();
			Object convertedValue = null;
			
			if (!skipConvert) {
				convertedValue = quickConvert(objValue, type);
	    		convertedValue = handleArrayType(widgetAttribute, type, convertedValue);
			} else {
				convertedValue = objValue;
			}

            String strValue = null;
            
            if (objValue instanceof String) {
            	strValue = (String) objValue;
            }
            
            if (attributeCommandChainMap != null) {
            	AttributeCommandChain attributeCommandChain = attributeCommandChainMap.get(widgetAttribute.getAttributeName());
				if (attributeCommandChain != null) {
					attributeCommandChain.setOriginalStringValue(strValue);
					attributeCommandChain.setOriginalValue(convertedValue);
					attributeCommandChain.setAttributeName(widgetAttribute.getAttributeName());

					if (phase == null) {
						convertedValue = attributeCommandChain.getValue(this);
					} else {
						convertedValue = attributeCommandChain.getValue(this, childWidget == null ? null : childWidget.asNativeWidget(), phase);
					}
            	}
            }

            if (convertedValue != null) {
	            ILifeCycleDecorator lifeCycleDecorator = getDecorator(widgetAttribute);
	
	            if (lifeCycleDecorator != null) {
	            	lifeCycleDecorator.setAttribute(widgetAttribute, strValue, convertedValue);
	            }
	            if (childWidget == null) {
	            	setAttribute(widgetAttribute, strValue, convertedValue, lifeCycleDecorator);
	            } else {
	            	((HasWidgets) this).setChildAttribute(childWidget, widgetAttribute, strValue, convertedValue);
	            }
	            
	            if (isInitialised() && updateAttributes != null) {
		            // this is fired from the execute command flow
	            	String attributeName = widgetAttribute.getAttributeName();
					if (updateAttributes.containsKey(attributeName)) {
	            		List<String> attributes = updateAttributes.get(attributeName);
	            		if (attributes != null) {
		            		for (String attribute : attributes) {
			            		AttributeCommandChain commandExecutor = attributeCommandChainMap.get(attribute);
								applyStyleToWidget(WidgetFactory.getAttribute(getLocalName(), attribute), commandExecutor.getOriginalStringValue());
							}
	            		}
	            	}
	            }
            }
		
        } catch (Exception e) {
        	System.out.println("error -> " + this.getClass().getName() + " " + e.getMessage());
        	e.printStackTrace();
        	fragment.addError(new com.ashera.model.Error(widgetAttribute, this, e));
        }
	}


	private Object handleArrayType(WidgetAttribute widgetAttribute, String type, Object convertedValue) {
		if ("array".equals(type)) {
			String converterTypeForArray = widgetAttribute.getArrayType();
			String arrayListToFinalType = widgetAttribute.getArrayListToFinalType();

			convertedValue = handleArrayType(convertedValue, converterTypeForArray, arrayListToFinalType);
		}
		return convertedValue;
	}

	private Object handleArrayType(Object objValue, String arrayType, String finalArrayType) {
		if  (arrayType != null) {
			String[] values = (String[]) objValue; 
			List<Object> finalValues = new ArrayList<Object>(values.length);
			for (String value : values) {
				finalValues.add(quickConvert(value, arrayType));
			}
			if (finalArrayType != null) {
				objValue = quickConvert(finalValues, finalArrayType);
			} else {
				objValue = finalValues;
			}
		}
		return objValue;
	}


	protected Map<String, Object> getDependentAttributesMap(IConverter converter) {
		Map<String, Object> dependentAttributesMap = new HashMap<>();
		if (converter != null) {
			List<String> dependentAttributes = PluginInvoker.getDependentAttributes(converter);

			if (dependentAttributes != null) {
				for (String dependentAttribute : dependentAttributes) {
					Object convertedValue = getConvertedValue(dependentAttribute);

					if (convertedValue != null) {
						dependentAttributesMap.put(dependentAttribute, convertedValue);
					}
				}
			}
		}
		return dependentAttributesMap;
	}

	private Object getConvertedValue(String attr) {
		if (attributes != null) {
			WidgetAttribute widgetAttribute = attributes.getWidgetAttribute(attr);
			if (widgetAttribute != null) {
				List<WidgetAttributeValue> widgetAttributeValues = attributes.get(widgetAttribute);

				if (widgetAttributeValues == null || widgetAttributeValues.isEmpty()) {
					return null;
				}

				Object objValue = null;

				for (WidgetAttributeValue widgetAttributeValue : widgetAttributeValues) {
					if (checkIfAttributeMatches(widgetAttributeValue)) {
						String strValue = widgetAttributeValue.getValue();
						IConverter converter = PluginInvoker.getConverter(widgetAttribute.getAttributeType());
						objValue = strValue;

						if (converter != null && strValue != null) {
							 Map<String, Object> dependentAttributesMap = getDependentAttributesMap(converter);
							objValue = PluginInvoker.convertFrom(converter, dependentAttributesMap, objValue, fragment);
						}
						break;
					}
				}

				return objValue;
			}
			return null;
		} else {
			return null;
		}
	}

	private ILifeCycleDecorator getDecorator(WidgetAttribute widgetAttribute) {	
		String decorator = widgetAttribute.getDecorator();
		if (decorator != null) {
			if (cachedDecorators == null) {
				cachedDecorators = new HashMap<>();
			}
			if (!cachedDecorators.containsKey(decorator)) {
				ILifeCycleDecorator lifeCycleDecor = WidgetFactory.getLifeCycleDecor(decorator, this);
				if (lifeCycleDecor != null) {
					cachedDecorators.put(decorator, lifeCycleDecor);
					addDecorator(lifeCycleDecor);
				}
			}
		}
		return cachedDecorators != null ? cachedDecorators.get(decorator) : null;
	}

	@Override
	public void create(IFragment fragment, Map<String, Object> params) {
		this.fragment = fragment;
		this.params = params;
		this.eventBus = fragment.getEventBus();
		
		if (params.containsKey("formGroupId")) {
			formGroupId = (String) params.get("formGroupId");
		}
	}

	
	// temporary data storage
	@Override
	public Object getUserData(String key) {
		if (userData == null) {
			userData = new HashMap<>();
		}
		
		return userData.get(key);		
	}
	
	@Override
	public void storeUserData(String key, Object object) {
		if (userData == null) {
			userData = new HashMap<>();
		}
		
		userData.put(key, object);
	}
	
	@Override
	public void storeInTempCache(String key, Object object) {
		if (tmpCache == null) {
			tmpCache = new HashMap<>();
		}
		
		tmpCache.put(key, object);
	}
	
	@Override
	public Object getFromTempCache(String key) {
		if (tmpCache == null) {
			tmpCache = new HashMap<>();
		}
		
		return tmpCache.get(key);		
	}


	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	// attribute getter methods	
	public abstract void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator);
	
	@Override
	public String getAttributeValue(String attr) {
		if (attributes != null) {
			List<WidgetAttributeValue> widgetAttributeValues = attributes.get(attr);
			if (widgetAttributeValues == null || widgetAttributeValues.isEmpty()) {
				return null;
			}
			
			String strValue = null;
			for (WidgetAttributeValue widgetAttributeValue : widgetAttributeValues) {
				if (checkIfAttributeMatches(widgetAttributeValue)) {
					strValue = widgetAttributeValue.getValue();
				}
			}
			
			return strValue; 
		} else {
			return null;
		}
	}
	
	public String getAttributeValue(String attr, WidgetAttributeMap attributes) {
		if (attributes != null) {
			List<WidgetAttributeValue> widgetAttributeValues = attributes.get(attr);
			if (widgetAttributeValues == null || widgetAttributeValues.isEmpty()) {
				return null;
			}
			
			String strValue = null;
			for (WidgetAttributeValue widgetAttributeValue : widgetAttributeValues) {
				if (checkIfAttributeMatches(widgetAttributeValue)) {
					strValue = widgetAttributeValue.getValue();
				}
			}
			
			return strValue; 
		} else {
			return null;
		}
	}
	
	public WidgetAttributeValue getAttributeValue(WidgetAttribute attr, WidgetAttributeMap attributes) {
		if (attributes != null) {
			List<WidgetAttributeValue> widgetAttributeValues = attributes.get(attr);
			if (widgetAttributeValues == null || widgetAttributeValues.isEmpty()) {
				return null;
			}
			
			WidgetAttributeValue strValue = null;
			for (WidgetAttributeValue widgetAttributeValue : widgetAttributeValues) {
				if (checkIfAttributeMatches(widgetAttributeValue)) {
					strValue = widgetAttributeValue;
					break;
				}
			}
			
			return strValue; 
		} else {
			return null;
		}
	}
	
	@Override
	public HasWidgets getParent() {
		return parent;
	}

	@Override
	public void setParent(HasWidgets parent) {
		this.parent = parent;
	}

	@Override
	public WidgetAttributeMap getAttributes() {
		return attributes;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	private String stripOS(String key) {
		String os = PluginInvoker.getOS();
		if (key.endsWith("-" + os.toLowerCase())) {
			key = key.substring(0, key.lastIndexOf("-" + os.toLowerCase()));
		}
		return key;
	}

	public WidgetAttribute getAttribute(
			final HasWidgets parent, final String localName,
			String key) {
		key = stripOS(key);
		WidgetAttribute attribute = null;
		if (key.startsWith("layout_") && parent != null) {
			if (parent.getLocalName().equals("template")) {
				attribute = WidgetFactory.getAttribute(parent.getParent().getLocalName(), key);
			} else {
				attribute = WidgetFactory.getAttribute(parent.getLocalName(), key);
			}
		} else{
			attribute = WidgetFactory.getAttribute(localName, key);
		}
		return attribute;
	}


	protected boolean checkIfAttributeMatches(WidgetAttributeValue attribute) {
		String orientation = PluginInvoker.getOrientation();
		int screenWidth = PluginInvoker.getScreenWidth();
		int screenHeight = PluginInvoker.getScreenHeight();
		boolean orientationCheck = attribute.getOrientation().equals("default") || attribute.getOrientation().equals(orientation);
		int minWidth = (int) convertToIntFromDp(attribute.getMinWidth());
		int maxWidth = (int) convertToIntFromDp(attribute.getMaxWidth());
		int minHeight = (int) convertToIntFromDp(attribute.getMinHeight());
		int maxHeight = (int) convertToIntFromDp(attribute.getMaxHeight());
		
		boolean minWidthCheck = minWidth == -1 || minWidth < screenWidth;
		boolean maxWidthCheck = maxWidth == -1 || maxWidth > screenWidth;
		boolean minHeightCheck = minHeight == -1 || minHeight < screenHeight;
		boolean maxHeightCheck = maxHeight == -1 || maxHeight > screenHeight;
		return orientationCheck && minWidthCheck && maxWidthCheck && minHeightCheck && maxHeightCheck;
	}
	
	// TODO : HMMM
	private int convertToIntFromDp(String dimension) {
		if (dimension == null) {
			return -1;
		}
		try {
			return (int) PluginInvoker.convertDpToPixel(dimension);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public void updateWidgetMap(WidgetAttributeMap attributes) {
		this.attributes = attributes;
		
		Iterator<WidgetAttribute> iter = attributes.keySet().iterator();
		
		while (iter.hasNext()) {
			WidgetAttribute key = (WidgetAttribute) iter.next();
			if (key.isApplyBeforeChildAdd()) {
				List<WidgetAttributeValue> values = attributes.get(key);
				
				for (WidgetAttributeValue value : values) {
					if (value != null) {
						if (checkIfAttributeMatches(value)) {
							applyStyleToWidget(key, value.getValue());
						}
					}
				}
				
			}
		}
	}

	public void updateMeasuredDimension(int width, int height) {
	}
	public void updateWidgetMap(WidgetAttribute attribute, List<WidgetAttributeValue> list) {
		attributes.put(attribute, list);
	}
	public void updateWidgetMap(WidgetAttribute key, WidgetAttributeValue value) {
		attributes.put(key, value);
		
		if (key.isApplyBeforeChildAdd()) {
			if (value != null) {
				if (checkIfAttributeMatches(value)) {
					applyStyleToWidget(key, value.getValue());
				}
			}
		}
	}
	
    @Override
    public void executeCommand(IWidget w, List<Object> commandDataArr, CommandCallBack command, int methods) {
        for (Object commandObj : commandDataArr) {
			Map<String, Object> map = PluginInvoker.getMap(commandObj);
            try {
				
				if (map.containsKey("id")) {
				    String id = PluginInvoker.getString(map.get("id"));
				    IWidget widget = w.findWidgetById(id);
				    if (widget != null) {
				    	widget.executeCommand(map, null, methods);
				    }
				} else if (map.containsKey("paths")) {                                
				    handlePath(map, w, methods);
				}
            } catch (Exception e) {
            	e.printStackTrace();
            	fragment.addError(new com.ashera.model.Error(map, this, e));
            }
        }       
    }
    
    private void handlePath(Map<String, Object> map, IWidget w, int methods) {
        List<Object> paths = PluginInvoker.getList(map.get("paths"));
        IWidget widget = w;
        List<IWidget> pathWidgets = new ArrayList<>();
        for (Object pathObj : paths) {
            String path = PluginInvoker.getString(pathObj);
            
            if (path.startsWith("@+id/")) {
                widget = widget.findWidgetById(path);
                pathWidgets.add(widget);
            }
            if (path.startsWith("@+pos/")) {
                if (widget instanceof HasWidgets) {
                    widget = ((HasWidgets) widget).get(PluginInvoker.getInt(path.substring("@+pos/".length())));
                    pathWidgets.add(widget);
                }
            }
        }
        if (pathWidgets.size() ==  paths.size()) {
            widget.executeCommand(map, null, methods);
        }
    }
	
	@Override
	public void executeCommand(Map<String, Object> payLoad, CommandCallBack command, int methods) {
	    List<String> keySet = new ArrayList<>(payLoad.keySet());
		// order command attributes by order of invocation
        if ((methods & IWidget.COMMAND_EXEC_SETTER_METHOD) != 0) {
            java.util.Collections.sort(keySet, (a ,b) -> compareByValue(payLoad, a, b, "orderSet"));
        } else if ((methods & IWidget.COMMAND_EXEC_GETTER_METHOD) != 0) {
            java.util.Collections.sort(keySet, (a ,b) -> compareByValue(payLoad, a, b, "orderGet"));
        }
        
		if (command != null) {
			command.onWidget(this);
		}
		int updateUiFlag = UPDATE_UI_NONE;
		for (String key : keySet) {
			if (key.equals("event-data")) {
				storeUserData(key, payLoad.get(key));
			} else if (key.startsWith("child:")) {
				IWidget child = findWidgetById(key.substring(key.indexOf(":") + 1));
				if (child != null) {
					child.executeCommand(PluginInvoker.getMap(payLoad.get(key)), command, methods);
				}
			} else if (this.getParent() instanceof ICompositeDecorator && ((ICompositeDecorator)this.getParent()).supportsAttribute(key)) {
				this.getParent().executeCommand(payLoad, command, methods);
			} else if (key.startsWith("layoutParams")) {
				Map<String, Object> commandObj = PluginInvoker.getMap(payLoad.get("layoutParams"));
				executeCommand(commandObj, command, methods);			
			} else {
				WidgetAttribute widgetAttribute = getAttribute(getParent(), this.getLocalName(), key);
				if (widgetAttribute != null) {
					if (attributes.containsKey("decorator")) {
						widgetAttribute.setDecorator(attributes.get("decorator").get(0).getValue());
					}
					updateUiFlag |= widgetAttribute.getUpdateUiFlag();

					Object objValue = payLoad.get(key);
					// decide the kind of 
					Map<String, Object> commandObj = PluginInvoker.getMap(objValue);
					
					if (commandObj != null) {
						if ((methods & IWidget.COMMAND_EXEC_SETTER_METHOD) != 0) {
							boolean isSetter = getBoolean(commandObj.get("setter"));
							if (isSetter) {
								objValue = commandObj.get("value");
								
								getAttributes().put(widgetAttribute, new WidgetAttributeValue(objValue != null ? objValue.toString() : null));

								if (key.startsWith("layout_")) {
									((BaseWidget) getParent()).applyStyleToWidget(widgetAttribute, this, objValue, null);
								} else {
									applyStyleToWidget(widgetAttribute, objValue);
								}
							}
						}

						if ((methods & IWidget.COMMAND_EXEC_GETTER_METHOD) != 0) {
							boolean isGetter = getBoolean(commandObj.get("getter"));
							if (isGetter) { 
								if (key.startsWith("layout_")) {
									objValue = ((BaseWidget) getParent()).getAttributeValueFromWidget(widgetAttribute, this, false);
								} else {
									objValue = getAttributeValueFromWidget(widgetAttribute, null, false);
								}
								PluginInvoker.putJSONSafeObjectIntoMap(commandObj, "commandReturnValue", objValue);
							}
						}
					
					}
				}
			}
		}	
		
		requestLayoutNInvalidateIfRequired(updateUiFlag);
	}
	
	@Override
	public Object getAttribute(String attributeName, boolean skipConvert) {
		Object objValue = null;
		if (attributeName.startsWith("layout_")) {
			WidgetAttribute widgetAttribute = WidgetFactory.getAttribute(getParent().getLocalName(), attributeName);
			objValue = ((BaseWidget) getParent()).getAttributeValueFromWidget(widgetAttribute, this, skipConvert);
		} else {
			WidgetAttribute widgetAttribute = WidgetFactory.getAttribute(localName, attributeName);
			objValue = getAttributeValueFromWidget(widgetAttribute, null, skipConvert);
		}
		
		return objValue;
	}

	public void requestLayoutNInvalidateIfRequired(int updateUiFlag) {
		if ((updateUiFlag & UPDATE_UI_REQUEST_LAYOUT) != 0) {
			requestLayout();
		}
		
		if ((updateUiFlag & UPDATE_UI_INVALIDATE) != 0) {
			invalidate();
		}
	}

    private int compareByValue(Map<String, Object> payLoad, String a, String b, String orderKey) {
        Object valueA = payLoad.get(a);
        Object valueB = payLoad.get(b);
        
        Map<String, Object> mapA = (Map<String, Object>) PluginInvoker.getMap(valueA); // if we get non-null value,it is a map
        Map<String, Object> mapB = (Map<String, Object>) PluginInvoker.getMap(valueB); // if we get non-null value,it is a map
        if (mapA != null && mapB != null) {
            Object objectA = mapA.get(orderKey);
            Object objectB = mapB.get(orderKey);

            if (objectA != null && objectB != null) {
                int orderKeyA = PluginInvoker.getInt(objectA);
    			int orderKeyB = PluginInvoker.getInt(objectB);
                return orderKeyA - orderKeyB;
            }
        } else if (mapA != null) {
            return 0;
        }
        return -1;
    }

	private Object getAttributeValueFromWidget(WidgetAttribute widgetAttribute, IWidget childWidget, boolean skipConvert) {
		Object objValue;
        ILifeCycleDecorator lifeCycleDecorator = getDecorator(widgetAttribute);

        if (childWidget != null) {
        	objValue = ((HasWidgets) this).getChildAttribute(childWidget, widgetAttribute);
        } else if (lifeCycleDecorator != null) {
            objValue = lifeCycleDecorator.getAttribute(widgetAttribute);
        }  else {
            objValue = getAttribute(widgetAttribute, getDecorator(widgetAttribute));
        }
		
        if (!skipConvert) {
			IConverter converter = PluginInvoker.getConverter(widgetAttribute.getAttributeType());
			if (converter != null && objValue != null) {
				objValue = PluginInvoker.convertTo(converter, objValue, fragment);
				
			}
        }
		return objValue;
	}

	// life cycle method
	public IWidgetLifeCycleListener getListener() {
		return listener;
	}

	public void setListener(IWidgetLifeCycleListener listener) {
		this.listener = listener;
	}
	
	public void restoreToDefaultState() {
		
	}
	
	// find widgets
	@Override
	public IWidget findWidgetById(String id) {
		IWidget idWidget = null;
		String attributeValue = getId();
		String componentId = null;
		if (id != null && id.contains("#")) {
			componentId = id.substring(0, id.lastIndexOf("#"));
			id = id.substring(id.lastIndexOf("#") + 1);
		}
		
		if (id != null && attributeValue != null && attributeValue.equals(id) && (componentId == null || componentId.equals(this.componentId))) {
			idWidget = this;
		}
		
		return idWidget;
	}
	
	
	// IModel related methods
	private String modelParam;
    private String modelIdPath;
	private String modelSyncEvents;
	private String modelPojoToUi;
    private String modelUiToPojo;
    private LoopParam loopParam;
	private String modelPojoToUiParams;
	private String modelUiToPojoEventIds;
	private boolean invalidateOnFrameChange;
    
	public boolean isInvalidateOnFrameChange() {
		return invalidateOnFrameChange;
	}

	public void setInvalidateOnFrameChange(boolean invalidateOnFrameChange) {
		this.invalidateOnFrameChange = invalidateOnFrameChange;
	}

	@Override
    public String getModelPojoToUiParams() {
		return modelPojoToUiParams;
	}

	@Override
    public String getModelPojoToUi() {
        return modelPojoToUi;
    }

    @Override
    public String getModelUiToPojo() {
        return modelUiToPojo;
    }


    @Override
    public String getModelParam() {
        return modelParam;
    }

    @Override
	public LoopParam getLoopParam() {
		return loopParam;
	}

    @Override
	public void setLoopParam(LoopParam loopParam) {
		this.loopParam = loopParam;
	}

	public BaseWidget(String groupName, String localName) {
		this.localName = localName;
		this.groupName = groupName;
	}
	
	@Override
	public String getModelIdPath() {
		return modelIdPath;
	}

	@Override
	public void setModelIdPath(String modelIdPath) {
		this.modelIdPath = modelIdPath;
	}
	@Override
	public String getModelSyncEvents() {
		return modelSyncEvents;
	}

	
	@Override
	public java.lang.String getModelUiToPojoEventIds() {
		return this.modelUiToPojoEventIds;
	}
	
	@Override
	public void setModelUiToPojoEventIds(String val) {
		this.modelUiToPojoEventIds = val;
	}
	
	@Override
	public void setModelSyncEvents(String modelSyncEvents) {
		this.modelSyncEvents = modelSyncEvents;
	}

	@Override
	public void setModelParam(String modelParam) {
		this.modelParam = modelParam;
		storeModelToScopeFromModelParam();
	}

	@Override
	public void setModelPojoToUi(String syncExpression) {
		this.modelPojoToUi = syncExpression;
	}
	
	@Override
	public void setModelPojoToUiParams(String params) {
		this.modelPojoToUiParams = params;
	}
	
	@Override
	public void setModelUiToPojo(String syncExpression) {
		this.modelUiToPojo = syncExpression;
	}
	
	protected boolean applyModelAttributes() {
		return true;
	}

	public void removeObjectListeners(IWidget widget) {
	    List<EventBusHandler> widgetEventBusHandlers = widget.getEventBusHandlers();
	    if (widgetEventBusHandlers != null) {
	        eventBus.off(widgetEventBusHandlers);
	        widgetEventBusHandlers.clear();
	    }
	    if (widget instanceof HasWidgets) {
	        for (Iterator<IWidget> iterator = ((HasWidgets) widget).iterator(); iterator.hasNext();) {
	            IWidget childWidget = iterator.next();
	            removeObjectListeners(childWidget);                
            }
	    }
	}
	

	@Override
    public List<EventBusHandler> getEventBusHandlers() {
        return eventBusHandlers;
    }
  

    public void applyModelToWidget() {
    	try {
    		if (this.modelPojoToUi != null) {
    			syncPojoToUi();
    		}
    	} catch (Exception e) {
        	e.printStackTrace();
        	fragment.addError(new com.ashera.model.Error((Map<String, Object>) null, this, e));
        }	        
    }

	private void syncPojoToUi() {
	    List<ModelPojoToUiHolder> modelPojoToUiHolders = ModelExpressionParser.parsePojoToUiExpression(this.modelPojoToUi);
	    
	    for (ModelPojoToUiHolder modelPojoToUiHolder : modelPojoToUiHolders) {
    		String modelAttribute = modelPojoToUiHolder.modelAttribute;
    		String varPath = modelPojoToUiHolder.varPath;
    		String key = modelPojoToUiHolder.key;
    		ModelScope varScope = modelPojoToUiHolder.varScope;
    		Object obj = getModelFromScope(key, varScope);
    		obj = getModelByPath(varPath, obj);
    		String methodName = modelPojoToUiHolder.methodName;
			if (methodName != null) {
				obj = ExpressionMethodHandler.getValue(methodName, obj, this);
			}
			
			WidgetAttribute widgetAttribute = getAttribute(this.getParent(), this.getLocalName(), modelAttribute);
    		applyStyleToWidget(widgetAttribute, obj);
    		requestLayoutNInvalidateIfRequired(widgetAttribute.getUpdateUiFlag());
	    }
	}

	private void storeModelToScopeFromModelParam() {
	    if (this.modelParam != null && !this.modelParam.equals("")) {
	        
    		//let x = . from y->intent into view as pathmap
	        List<ModelVarHolder> modelVarHolders = ModelExpressionParser.parseModelVarExpression(this.modelParam);
	        for (ModelVarHolder modelVarHolder : modelVarHolders) {
        		String varName = modelVarHolder.varName;
        		String varPath =modelVarHolder.varPath;
        		String key = modelVarHolder.key;
        		ModelScope varSource = modelVarHolder.varSource;
        		ModelScope varScope = modelVarHolder.varScope;
        		ModelDataType dataType = modelVarHolder.dataType;
        		
        		Object obj = getModelFromScope(key, varSource);
        		obj = getModelByPath(varPath, obj);
        		obj = changeModelDataType(dataType, obj);
        		storeModelToScope(varName, varScope, obj);
	        }
	    }
	}

    @Override
    public void requestLayout() {
    	
    }
    
    @Override
    public void invalidate() {
    }
    
    @Override
    public void setVisible(boolean b) {
    }
    
    @Override
    public boolean isInitialised() {
        return initialised;
    }
    @Override
    public int getBaseLine() {
    	return -1;
    }

    @Override
    public void updateModelToEventMap(java.util.Map<String, Object> eventMap, String eventType, String eventParams) {
    	this.updateModelToEventMap(eventMap, eventParams, this.getLoopParam());
    	
    }
	public void updateModelToEventMap(java.util.Map<String, Object> eventMap, String eventParams,
			LoopParam loopParam) {
		if (eventParams != null && !eventParams.isEmpty()) {
			List<ModelEventHolder> modelEventHolders = ModelExpressionParser.parseEventExpression(eventParams);
			
			for (ModelEventHolder modelEventHolder : modelEventHolders) {
			    String key = modelEventHolder.key;
			    String varPath = modelEventHolder.varPath;
			    String varName = modelEventHolder.varName;
			    String methodName = modelEventHolder.methodName;
			    ModelScope varScope = modelEventHolder.varScope;
			    Object obj = getModelFromScope(varName, varScope, loopParam);
			    obj = getModelByPath(varPath, obj);
			    
			    if (obj instanceof IFlatMap) {
			        obj = ((IFlatMap) obj).getHierarchicalMap();
			    }
			    
			    String idPath = getModelIdPath();
			    if (idPath != null) {
			    	Object myobj = getModelByPath(idPath, obj);
			    	if (methodName != null) {
			    		myobj = ExpressionMethodHandler.getValue(methodName, myobj, this);
					}
					PluginInvoker.putJSONSafeObjectIntoMap(eventMap, idPath, myobj);
			    }
			    if (methodName != null) {
			    	obj = ExpressionMethodHandler.getValue(methodName, obj, this);
				}
			    PluginInvoker.putJSONSafeObjectIntoMap(eventMap, key, obj);
			}
		}
	}

    @Override
	public void syncModelFromUiToPojo(String eventType) {
		if (modelUiToPojo != null) {
            if (modelSyncEvents != null) {
                java.util.List<String> events = Arrays.asList(modelSyncEvents.split(","));
                
                if (events.contains(eventType)) {
                    
                    if (modelUiToPojo != null) {
                        handleModelUiToPojo();
                    }
                }
            }           
        }
	}

	private void handleModelUiToPojo() {
		List<ModelUiToPojoHolder> modelUiToPojoHolders = ModelExpressionParser.parseUiToPojoExpression(this.modelUiToPojo);
		
		for (ModelUiToPojoHolder modelUiToPojoHolder : modelUiToPojoHolders) {
    		String varPath = modelUiToPojoHolder.varPath;
    		String varAttributeName = modelUiToPojoHolder.modelAttribute;
    		String varName = modelUiToPojoHolder.varName;
    		ModelScope varScope = modelUiToPojoHolder.varScope;
    		WidgetAttribute widgetAttribute = getAttribute(getParent(), getLocalName(), varAttributeName);
    		
    		String key = widgetAttribute.getAttributeName();
    		Object objValue;
    		if (key.startsWith("layout_")) {
    			objValue = ((BaseWidget) getParent()).getAttributeValueFromWidget(widgetAttribute, this, false);
    		} else {
    			objValue = getAttributeValueFromWidget(widgetAttribute, null, false);
    		}
    
    		Object obj = getModelFromScope(varName, varScope);
    
			objValue = PluginInvoker.getJSONSafeObj(objValue);
			objValue = changeModelDataType(modelUiToPojoHolder.varType, objValue);
			objValue = PluginInvoker.getJSONSafeObj(objValue);
			if (varPath.equals(".")) {
				storeModelToScope(varName, varScope, objValue);
			} else {
			    updateModelByPath(varPath, objValue, obj);
			}
		}
	}

	@Override
    public Object getModelByPath(String varPath, Object obj) {
        if (!varPath.equals(".")) {
		    if (obj instanceof IFlatMap) {
                obj = ((IFlatMap) obj).get(varPath);
            } else if (varPath.contains(".") || varPath.contains("[")) {
            	obj = new com.ashera.model.PlainMap(PluginInvoker.getMap(obj)).get(varPath);
            } else {
            	if (obj != null) { 
            		obj = PluginInvoker.getMap(obj).get(varPath);
            	}
            }
		}
        return obj;
    }

	@Override
	public void updateModelByPath(String varPath, Object objValue, Object obj) {
        try {
        	if (!varPath.equals(".")) {
	        	if (obj instanceof IFlatMap) {
				    ((IFlatMap) obj).put(varPath, objValue);
				} else if (varPath.contains(".") || varPath.contains("[")) {
					new com.ashera.model.PlainMap(PluginInvoker.getMap(obj)).put(varPath, objValue);
				} else {
					if (obj != null) { 
						((Map<String, Object>)PluginInvoker.getMap(obj)).put(varPath, objValue);
					}
				}
        	}
		} catch (PlainMapException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public void storeModelToScope(String varName, ModelScope varScope, Object objValue) {
    	ModelStore.storeModelToScope(varName, varScope, objValue, fragment, this, null);
    }
    
    public void storeModelToScope(String varName, ModelScope varScope, Object objValue, LoopParam loopParam) {
    	ModelStore.storeModelToScope(varName, varScope, objValue, fragment, this, loopParam);
    }

    @Override
    public Object getModelFromScope(String varName, ModelScope varScope) {
        return ModelStore.getModelFromScope(varName, varScope, fragment, this, this.loopParam);
    }
    
    
    public Object getModelFromScope(String varName, ModelScope varScope, LoopParam loopParam) {
        return ModelStore.getModelFromScope(varName, varScope, fragment, this, loopParam);
    }

    
    public Object changeModelDataType(ModelDataType dataType, Object obj) {
        return ModelStore.changeModelDataType(dataType, obj);
    }
    
    @Override
    public void notifyDataSetChanged() {
        applyModelToWidget();
    }
    
    @Override
    public void updateModelData(String expression, Object data) {
        ModelStoreVarHolder modelStoreVarHolder = ModelExpressionParser.parseModelStoreVarExpression(expression);
        String varName = modelStoreVarHolder.varName;
        ModelScope varScope = modelStoreVarHolder.varScope;
        ModelDataType varType = modelStoreVarHolder.varType;
        Object modelData = changeModelDataType(varType, data);
        ModelStore.storeModelToScope(varName, varScope, modelData, fragment, this, null);    	
    }
    
    @Override
    public IWidget findNearestView(String id) {
		String componentId = null;
		String widgetId = id;
		if (id.contains("#")) {
			componentId = id.substring(0, id.lastIndexOf("#"));
			widgetId = id.substring(id.lastIndexOf("#") + 1);
		}

		if (widgetId.equals(getId()) && (componentId == null || componentId.equals(this.componentId))) {
    		return this;
    	}
    	
    	if (this instanceof HasWidgets) {
    		HasWidgets hasWidgets = (HasWidgets) this;

			for (Iterator<IWidget> iterator = hasWidgets.iterator(); iterator.hasNext();) {
				IWidget w = iterator.next();

				if (widgetId.equals(w.getId()) && (componentId == null || componentId.equals(w.getComponentId()))) {
		    		return w;
		    	}
				
				if (w instanceof HasWidgets) {
					IWidget result = w.findWidgetById(id);
					if (result != null) {
						return result;
					}
				}
			}
    	}
    	if (getParent() == null) {
    		return null;
    	}
    	return getParent().findNearestView(id);
	
    }
    
    protected boolean getBoolean(Object val) {
    	return com.ashera.utils.StringUtils.getBoolean(val);
    }
    
    public boolean hasFeature(String key, String featureName) {
    	String os = PluginInvoker.getOS();
    	String value = null;
    	if (params.containsKey(key + "-" + os)) {
    		value = (String) params.get(key + "-" + os);
    	} else if (params.containsKey(key)) {
    		value = (String) params.get(key);
    	}
    	
    	if (value != null) {
    		return Arrays.asList(value.split("\\|")).contains(featureName);
    	}
    	return false;
    }
    
    @Override
    public void runAttributeCommands(Object nativeWidget, String phase, String commandFilterRegex, Object... args) {
    	try {
	    	if (commandPhases != null) {
	    		Set<String> phaseAttributes = commandPhases.get(phase);
				if (phaseAttributes != null) {
					for (String attribute : phaseAttributes) {
						AttributeCommandChain attributeCommandChain = getAttributeCommandChain(attribute);
				    	if (attributeCommandChain != null) {
				    		attributeCommandChain.getValue(this, nativeWidget, phase, commandFilterRegex, args);
				    	}
					}
				}
	    	}
    	} catch (Exception e) {
    		fragment.addError(new com.ashera.model.Error((Map<String, Object>) null, this, e));
    	}
    }

	@Override
	public void addDecorator(ILifeCycleDecorator decorator) {
		if (methodListeners == null) {
			methodListeners = new HashMap<>();
		}

		for (String method : decorator.getMethods()) {
			if (!methodListeners.containsKey(method)) {
				methodListeners.put(method, new ArrayList<>());
			}
			methodListeners.get(method).add(decorator);
		}
	}

	@Override
	public void executeMethodListeners(String methodName, Object... args) {
		executeMethodListeners(methodName, null, args);
	}
	
	@Override
	public boolean hasMethodListener(String methodName) {
		return methodListeners != null && methodListeners.containsKey(methodName);
	}
	
	@Override
	public void executeMethodListeners(String methodName, Runnable callback, Object... args) {
		if (hasMethodListener(methodName)) {
			onMethodCalled = false;
			for (ILifeCycleDecorator listener: methodListeners.get(methodName)) {
				listener.execute(methodName, args);
			}
			if (!onMethodCalled) {
				if (callback != null) {
					callback.run();
				}
			}
		} else {
			if (callback != null) {
				callback.run();
			}
		}
	}
	
	@Override
	public void setOnMethodCalled(boolean onMethodCalled) {
		this.onMethodCalled = onMethodCalled;
	}
	
	// use only for hacks related to android
	public boolean setFieldUsingReflection(Object targetObject, String fieldName, Object fieldValue) {
        Field field;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        Class superClass = targetObject.getClass().getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }
        if (field == null) {
            return false;
        }
        field.setAccessible(true);
        try {
            field.set(targetObject, fieldValue);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
	
	public Object getFieldValueUsingReflection(Object targetObject, String fieldName) {
        Field field;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        Class superClass = targetObject.getClass().getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        try {
            return field.get(targetObject);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
	public static Object invokePrivateMethodUsingReflection(Object obj, String methodName,
            Object... params) {
        int paramCount = params.length;
        Method method = null;
        Object requiredObj = null;
        Class<?>[] classArray = new Class<?>[paramCount];
        for (int i = 0; i < paramCount; i++) {
            classArray[i] = params[i].getClass();
        }
        Class superClass = obj.getClass();
        try {
        	while(method == null && superClass != null) {
                try {
                	method = superClass.getDeclaredMethod(methodName, classArray);
                } catch (NoSuchMethodException var8) {
                    superClass = superClass.getSuperclass();
                }
            }
        	
            method.setAccessible(true);
            requiredObj = method.invoke(obj, params);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

        return requiredObj;
    }
	

	@Override
	public Object createWrapperViewHolder(int viewType) {
		return null;
	}

	@Override
	public Object createWrapperView(Object wrapperParent, int viewtype) {
		return null;
	}

	@Override
	public void addForegroundIfNeeded() {
		
	}

	@Override
	public Object getForeground() {
		return null;
	}
	
	@Override
	public boolean isAfterParentInitRequired() {
		return false;
	}
	
	@Override
	public void afterParentInit() {
		
	}
	
	@Override
    public void drawableStateChanged() {
    	if (cachedDecorators != null) {
    		for (ILifeCycleDecorator lifeCycleDecorator : cachedDecorators.values()) {
    			lifeCycleDecorator.drawableStateChanged();
			}
    	}
	}
	
	@Override
	public String getGroupName() {
		return groupName;
	}
	
	@Override
	public void setDrawableBounds(int l, int t, int r, int b) {
		
	}
	@Override
	public void setEventBubblers(Collection<Integer> flags) {
		if (eventBubblers == null) {
			eventBubblers = new HashSet<>();
		}
		
		eventBubblers.addAll(flags);
	}
	
	@Override
	public Set<Integer> getEventBubblers() {
		return eventBubblers;
	}
	
	@Override
	public void applyThemeConstructorArgsStyle(String themeName, Map<String, Object> params) {
		if (params != null) {
			String constAttrStr = com.ashera.utils.ResourceBundleUtils.getString("values/theme", themeName + "_constructor",
					fragment);
			ModelExpressionParser.parseSimpleCssExpression(constAttrStr, params);
		}
	}
	
	@Override
	public void applyThemeStyle(String themeName) {
		String attrStr = com.ashera.utils.ResourceBundleUtils.getString("values/theme", themeName, fragment);
		if (attrStr != null && !attrStr.isEmpty()) {
	        String[] attrs = attrStr.split(";");
	        for (String attr : attrs) {
	        	String[] nameAndValue = attr.split("\\:");
	        	String key = nameAndValue[0];
	        	String value = nameAndValue.length <= 1 ? "" : nameAndValue[1];
	        	
	        	final WidgetAttribute attribute = getAttribute(parent, getLocalName(), key);
	        	if (attribute != null) {
	        		updateWidgetMap(attribute, new WidgetAttributeValue(value));
	        	}
			}
		}
	}
	
	@Override
    public IWidget loadLazyWidgets(HasWidgets parent, int index, String idKey, LoopParam model) {
        Object handler = PluginInvoker.getHandler(parent, index, fragment);
        return loadWidget(this, handler, idKey, model, index);
    }

	
	@Override
    public IWidget loadLazyWidgets(HasWidgets parent) {
        Object handler = PluginInvoker.getHandler(parent, -1, fragment);
        return loadWidget(this, handler, "", null, -1);
    }
	
	@Override
    public IWidget loadLazyWidgets(LoopParam model) {
        Object handler = PluginInvoker.getHandler(parent, -1, fragment);
        return loadWidget(this, handler, "", model, -1);
    }
    
    private void loadAndAddWidgets(Iterator<IWidget> iterator, Object handler, String idKey, LoopParam model) {
        while (iterator.hasNext()) {
            IWidget objWidget = (IWidget) iterator.next();
            loadWidget(objWidget, handler, idKey, model, -1);
        }
    }

    private IWidget loadWidget(IWidget objWidget, Object handler, String idKey, LoopParam model, int index) {
        IWidget widgetCreated = null;
        if (objWidget instanceof com.ashera.widget.BaseHasWidgets.LazyBaseWidget) {
            com.ashera.widget.BaseHasWidgets.LazyBaseWidget widget = (com.ashera.widget.BaseHasWidgets.LazyBaseWidget) objWidget;
            widgetCreated = PluginInvoker.handlerStart(handler, widget, index);
            widgetCreated.setLoopParam(model);

            loadAndAddWidgets(((HasWidgets) widget).iterator(), handler, idKey, model);
            PluginInvoker.handlerEnd(handler, widget);
        } else if (objWidget instanceof com.ashera.widget.BaseWidget.LazyBaseWidget) {
            com.ashera.widget.BaseWidget.LazyBaseWidget widget = (com.ashera.widget.BaseWidget.LazyBaseWidget) objWidget;
            widgetCreated = PluginInvoker.handlerStart(handler, widget, index);
            widgetCreated.setLoopParam(model);

            PluginInvoker.handlerEnd(handler, widget);
        } else {
            PluginInvoker.addToCurrentParent(handler, objWidget);
        }
        return widgetCreated;
    }

    // form related attributes
	private String formGroupId;
	private String errorStyle;
	private String normalStyle;
	private int validationErrorDisplayType;
	private List<String> customErrorMessageKeys;
	private List<String> customErrorMessageValues;

	public int getValidationErrorDisplayType() {
		return validationErrorDisplayType;
	}

	public void setValidationErrorDisplayType(int validationErrorDisplayType) {
		this.validationErrorDisplayType = validationErrorDisplayType;
	}


	public void setCustomErrorMessageKeys(List<String> customErrorMessageKeys) {
		this.customErrorMessageKeys = customErrorMessageKeys;
	}

	public void setCustomErrorMessageValues(List<String> customErrorMessageValues) {
		this.customErrorMessageValues = customErrorMessageValues;
	}
	
	public String getCustomMessage(String key) {
		if (customErrorMessageKeys != null) {
			int index = customErrorMessageKeys.indexOf(key);
			if (index != -1) {
				return customErrorMessageValues.get(index);
			}
		}
		
		return null;
	}

	public String getFormGroupId() {
		return formGroupId;
	}

	public String getErrorStyle() {
		return errorStyle;
	}

	public void setErrorStyle(String errorStyle) {
		this.errorStyle = errorStyle;
	}
	
	public String getNormalStyle() {
		return normalStyle;
	}

	public void setNormalStyle(String normalStyle) {
		this.normalStyle = normalStyle;
	}

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		return null;
	}
}
