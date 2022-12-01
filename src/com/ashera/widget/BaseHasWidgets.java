package com.ashera.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.ashera.converter.IConverter;
import com.ashera.core.IFragment;
import com.ashera.model.ExpressionMethodHandler;
import com.ashera.model.LoopParam;
import com.ashera.model.ModelDataType;
import com.ashera.model.ModelExpressionParser;
import com.ashera.model.ModelExpressionParser.ModelLoopHolder;
import com.ashera.model.ModelExpressionParser.ModelPojoToUiHolder;
import com.ashera.model.ModelScope;
import com.ashera.widget.WidgetViewHolder.AttributeViewHolder;

public abstract class BaseHasWidgets extends BaseWidget implements HasWidgets{
    protected List<IWidget> widgets = new ArrayList<IWidget>();
    protected List<IWidget> afterInitWidgets;
    protected IWidget listItem = null;

    public BaseHasWidgets(String groupName, String localName) {
		super(groupName, localName);
	}

	@Override
	public void add(IWidget w, int index) {
		if (index < 0) {
	        widgets.add(w);
	    } else {
	        widgets.add(index, w);
	    }
	    
	    if (w.isAfterParentInitRequired()) {
	    	if (afterInitWidgets == null) {
	    		afterInitWidgets = new ArrayList<IWidget>();
	    	}
	    	afterInitWidgets.add(w);
	    }
	    
		setChildAttributes(w);
	}

	private void setChildAttributes(IWidget w) {

		Set<WidgetAttribute> widgetAttributes = w.getAttributes().keySet();

		for (WidgetAttribute widgetAttribute : widgetAttributes) {
			try {
				if (widgetAttribute.isForChild()) {
					setChildAttribute(w, widgetAttribute);
				}
			} catch (Exception e) {
				e.printStackTrace();
				fragment.addError(new com.ashera.model.Error(widgetAttribute, this, e));
			}
		}

	}

	public void setChildAttribute(IWidget w, WidgetAttribute widgetAttribute) {
		IConverter converter = PluginInvoker.getConverter(widgetAttribute.getAttributeType());
		String strValue = w.getAttributeValue(widgetAttribute.getAttributeName());

		if (strValue != null) {
			Object objValue = strValue;
			if (converter != null) {
				objValue = PluginInvoker.convertFrom(converter, getDependentAttributesMap(converter), strValue, fragment);
			}
			setChildAttribute(w, widgetAttribute, strValue, objValue);
		}
	}

	public abstract void setChildAttribute(IWidget widget, WidgetAttribute key, String strValue, Object value);

	@Override
	public void clear() {
		int startIndex = 0;
    	int endIndex = getChildWidgets().size();
    	for (int i = endIndex - 1; i >= startIndex; i--) {
    		remove(i);
    	}
	}

	@Override
	public Iterator<IWidget> iterator() {
		return widgets.iterator();
	}

	@Override
	public boolean remove(IWidget w) {
		return widgets.remove(w);
	}

    @Override
    public boolean remove(int index) {
        removeIdsAndData(index);
        return removeWidget(index);
    }

    public boolean removeWidget(int index) {
        boolean success = false;
        if (index + 1 <= widgets.size()) {
            IWidget widget = widgets.get(index);
            widget.setParent(null);
            removeObjectListeners(widget);
            widgets.remove(index);
            success = true;
        }
        return success;
    }

    public void removeIdsAndData(int index) {
        if (index + 1 <= ids.size()) {
            ids.remove(index);
            dataList.remove(index);
        }
    }	
	
	@Override
	public IWidget newLazyInstance() {
		return new LazyBaseWidget();
	}
	
	public IWidget findWidgetById(String id) {
		IWidget idWidget = super.findWidgetById(id);
		
		if (idWidget == null) {
			for (Iterator<IWidget> iterator = iterator(); iterator.hasNext();) {
				IWidget w = iterator.next();
				idWidget = w.findWidgetById(id);
				
				if (idWidget != null) {
					break;
				}
			}
		}
		
		return idWidget;
	}
	
	@com.google.j2objc.annotations.WeakOuter
	public class LazyBaseWidget extends BaseHasWidgets {
		private static final String LOCAL_NAME = "LazyBaseWidget";
		public LazyBaseWidget() {
			super(LOCAL_NAME, LOCAL_NAME);
		}


		@Override
		public IWidget newInstance() {
			return 	WidgetFactory.get(BaseHasWidgets.this.getLocalName(), false);
		}


		@Override
		public Object asWidget() {
			return BaseHasWidgets.this;
		}

		@Override
		public Object asNativeWidget() {
			return null;
		}

		@Override
		public void create(IFragment fragment,  Map<String, Object> params) {
			super.create(fragment, params);
		}

		
		@Override
		public void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator) {
		}
		@Override
		public void setChildAttribute(IWidget widget, WidgetAttribute key, String strValue, Object value) {			
		}
		
		@Override
		public void initialized() {
		}
		
		@Override
		public String getLocalName() {
			return BaseHasWidgets.this.getLocalName();
		}
		
		@Override
		public String getGroupName() {
			return BaseHasWidgets.this.getGroupName();
		}

		@Override
		public void restoreToDefaultState() {
		}

		@Override
		public void loadAttributes(String localName) {
		}


		@Override
		public Object getAttribute(WidgetAttribute key, ILifeCycleDecorator decorator) {
			return null;
		}


        @Override
        public void addTemplate(Object objValue) {
            
        }


        @Override
        public void addModel(LoopParam model, String currentLoopParam) {
            
        }


        @Override
        public void addModel(LoopParam model, int index, String currentLoopParam) {
            
        }


        @Override
        public void removeModelById(String id) {
            
        }


        @Override
        public List<String> getStableIds() {
            return null;
        }


		@Override
		public Object getChildAttribute(IWidget w, WidgetAttribute key) {
			return null;
		}

	}
    
    // model related methods
    protected List<LoopParam> dataList = new ArrayList<>();
    protected List<String> ids = new ArrayList<>();
    private String modelFor;


    @Override
    public void setModelFor(String modelFor) {
    	this.modelFor = modelFor;
    }

    @Override
    public String getModelFor() {
        return this.modelFor;
    }

    @Override
    public void addTemplate(Object objValue) {
        this.listItem = ((IWidget) objValue);
    }

    @Override
    public void addAllModel(Object objValue) {
    	List<Object> models = PluginInvoker.getList(objValue);
    	
    	for (Object model : models) {
    		addModel(model);
		}
    }
    
    public void addModel(LoopParam model, String currentLoopVar) {
        addObject(model, getModelIdPath(), -1, currentLoopVar);
    }
    
    public void addModel(LoopParam model, int index, String currentLoopVar) {
        addObject(model, getModelIdPath(), index, currentLoopVar);        
    }
    
    protected void addObject(LoopParam childModel, String modelIdPath, int index, String currentLoopVar) {
        String id = null;
        if (modelIdPath != null) {
            id = PluginInvoker.getString(getModelByPath(modelIdPath, childModel.get(currentLoopVar)));
        } else {
            id = UUID.randomUUID().toString();
        }
        
        if (!ids.contains(id)) {
            if (index == -1) {
            	ids.add(id);
            	dataList.add(childModel); 
            } else {
            	ids.add(index, id);
            	dataList.add(index, childModel); 
            }
            
            addItemToParent(index, id, childModel);
        } else {
        	dataList.set(ids.indexOf(id), childModel);
        	IWidget widget;
        	widget = getChildWidgets().get(ids.indexOf(id));
        	updateModelRecurse(widget, childModel);
        }
        
    }
    
    /**
     * Override this if the list contains a subview.
     * 
     */
    protected List<IWidget> getChildWidgets() {
        return widgets;
    }

	protected void addItemToParent(int index, String id, LoopParam childModel) {
		IWidget widget = listItem.loadLazyWidgets(this, index, id, childModel);
		onChildAdded(widget);
	}

    protected void onChildAdded(IWidget widget) {
        
    }

    protected void updateModelRecurse(IWidget widget, LoopParam childModel, CommandCallBack callBack) {
        widget.setLoopParam(childModel);
    	widget.notifyDataSetChanged();
        
    	if (callBack != null) {
        	callBack.onWidget(widget);
        }
    	
        if (widget instanceof HasWidgets && ((HasWidgets) widget).getModelFor() == null) {
            for (IWidget childWidget : ((HasWidgets) widget).getWidgets()) {
                updateModelRecurse(childWidget, childModel, callBack);
            }
        }
    }
    protected void updateModelRecurse(IWidget widget, LoopParam childModel) {
    	updateModelRecurse(widget, childModel, null);
        
    }

    @Override
    public void removeModelById(String id) {
    	int index = ids.indexOf(id);
		if (index != -1) {
    		removeModelAtIndex(index);
    	}
    }
    
    @Override
    public List<String> getStableIds() {
        return ids;
    }

    @Override
    public IWidget get(int index) {
        return widgets.get(index);
    }
    
    @Override
    public List<IWidget> getWidgets() {
        return widgets;
    }
    
    public IWidget getListItem() {
        return listItem;
    }
    
    
    @Override
    public HasWidgets getCompositeLeaf() {
        return this;
    }
    
    @Override
    public boolean areWidgetItemsRecycled() {
    	return false;
    }

    
    @Override
    public void applyModelToWidget() {
        super.applyModelToWidget();
        try {
	        if (modelFor != null) {
	            ModelLoopHolder modelLoopHolder = ModelExpressionParser.parseModelLoopExpression(this.modelFor);
	            
	            //let x in . from y->intent into view as pathmap
	            String varName = modelLoopHolder.varName;
	            String varPath = modelLoopHolder.varPath;
	            String key = modelLoopHolder.key;
	            ModelScope varSource = modelLoopHolder.varSource;
	            ModelScope varScope = modelLoopHolder.varScope;
	            ModelDataType dataType = modelLoopHolder.dataType;
	            Object obj = getModelFromScope(key, varSource);
	            obj = getModelByPath(varPath, obj);
	            
	            if (obj != null) {
		            for (Object model: PluginInvoker.getList(obj)) {
		                model = changeModelDataType(dataType, model);
		                LoopParam loopParam = new LoopParam();
		                if (this.getLoopParam() != null) {
		                	loopParam.putAll(this.getLoopParam());
		                }
		                storeModelToScope(varName, varScope, model, loopParam);
		                addModel(loopParam, varName);
		            }
	            }
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	        fragment.addError(new com.ashera.model.Error((Map<String, Object>) null, this, e));
	    }

    }
    
    @Override
    public void addModel(Object model, int index) {
        addModelByIndex(model, index);
    }

    @Override
    public void addModel(Object model) {
        addModel(model, -1);
    }
    
    @Override
    public void removeModelAtIndex(int index) {
        if (index != -1) {
            String modelFor = getModelFor();
            if (modelFor != null) {
                ModelLoopHolder modelLoopHolder = com.ashera.model.ModelExpressionParser.parseModelLoopExpression(modelFor);
                List<Object> listObj = getListObjectInScope(modelLoopHolder);
                listObj.remove(index);
                remove(index);
            }
        }
    }
    
    private void addModelByIndex(Object model, int index) {
        String modelFor = getModelFor();
        if (modelFor != null) {
            ModelLoopHolder modelLoopHolder = com.ashera.model.ModelExpressionParser.parseModelLoopExpression(modelFor);
            
            //let x in . from y->intent into view as pathmap
            String varName = modelLoopHolder.varName;
            com.ashera.model.ModelScope varScope = modelLoopHolder.varScope;
            com.ashera.model.ModelDataType dataType = modelLoopHolder.dataType;
            List<Object> listObj = getListObjectInScope(modelLoopHolder);
            
            int existingIndex = -1;
            if (getModelIdPath() != null) {
            	String id = PluginInvoker.getString(getModelByPath(getModelIdPath(), model));
            	existingIndex = ids.indexOf(id);
            }
            
            if (existingIndex != -1) {
            	listObj.set(existingIndex, model);
            } else {

	            if (index == -1) {
	                listObj.add(model);    
	            } else {
	                listObj.add(index, model);
	            }
            }
            model = changeModelDataType(dataType, model);

            com.ashera.model.LoopParam loopParam = new com.ashera.model.LoopParam();
            storeModelToScope(varName, varScope, model, loopParam);
            addModel(loopParam, index, varName);        
        }
    }

    private List<Object> getListObjectInScope(ModelLoopHolder modelLoopHolder) {
        String varPath = modelLoopHolder.varPath;
        String key = modelLoopHolder.key;
        ModelScope varSource = modelLoopHolder.varSource;

        Object obj = getModelFromScope(key, varSource);
        List<Object> listObj = PluginInvoker.getList(getModelByPath(varPath, obj));
        return listObj;
    }
    
    
    
    @Override
    public void notifyDataSetChanged() {
    	clearModel();
    	super.notifyDataSetChanged();
    }

	protected void clearModel() {
		if (listItem != null) {
	    	ids.clear();
	    	dataList.clear();
	
	    	clear();
    	}
	}
	
	@Override
	public void initialized() {
		super.initialized();
		
		if (afterInitWidgets != null) {
			for (IWidget w : afterInitWidgets) {
				w.afterParentInit();
			}
		}

	}

	public WidgetViewHolder createWidgetViewHolder(List<String> viewHolderIds, IWidget widget) {
		WidgetViewHolder holder = new WidgetViewHolder();
		holder.widget = widget;
		if (viewHolderIds != null) {
			for (String viewHolderId : viewHolderIds) {
				if (!viewHolderId.startsWith("@+id/")) {
					viewHolderId = "@+id/" + viewHolderId; 
				}
				IWidget childWidget = widget.findWidgetById(viewHolderId);

				if (childWidget != null && childWidget.getModelPojoToUi() != null) {
					List<ModelPojoToUiHolder> modelPojoToUiHolders = ModelExpressionParser.parsePojoToUiExpression(childWidget.getModelPojoToUi());

					for (ModelPojoToUiHolder modelPojoToUiHolder : modelPojoToUiHolders) {
						AttributeViewHolder attrHolder = new AttributeViewHolder();
						String modelAttribute = modelPojoToUiHolder.modelAttribute;
						WidgetAttribute widgetAttribute = getAttribute(childWidget.getParent(), childWidget.getLocalName(), modelAttribute);
						attrHolder.widgetAttribute = widgetAttribute;
						attrHolder.childWidget = childWidget;
						attrHolder.modelPojoToUiHolder = modelPojoToUiHolder;
						holder.attributes.add(attrHolder);
					}
				} else {
					AttributeViewHolder attrHolder = new AttributeViewHolder();
					attrHolder.childWidget = childWidget;
					holder.attributes.add(attrHolder);
				}
			}
		}
		return holder;
	}
	
	public void setAttributeOnViewHolder(WidgetViewHolder widgetViewHolder, int position) {
		if (widgetViewHolder != null) {
			com.ashera.model.LoopParam model = dataList.get(position);
			setAttributeOnViewHolder(widgetViewHolder, model, false);
		}
	}

	public void setAttributeOnViewHolder(WidgetViewHolder widgetViewHolder, com.ashera.model.LoopParam model) {
		setAttributeOnViewHolder(widgetViewHolder, model, false);
	}
	public void setAttributeOnViewHolder(WidgetViewHolder widgetViewHolder, com.ashera.model.LoopParam model, boolean invalidateChild) {
		if (widgetViewHolder != null) {
			try {
				fragment.disableRemeasure();
				for (AttributeViewHolder attrHolder : widgetViewHolder.attributes) {
					IWidget childWidget = attrHolder.childWidget;
					childWidget.setLoopParam(model);
					if (invalidateChild) {
						invalidateChildIfRequired(childWidget);
					}
					ModelPojoToUiHolder modelPojoToUiHolder = attrHolder.modelPojoToUiHolder;
					if (modelPojoToUiHolder != null) {
						Object obj = childWidget.getModelFromScope(modelPojoToUiHolder.key, modelPojoToUiHolder.varScope);
						obj = getModelByPath(modelPojoToUiHolder.varPath, obj);
			    		String methodName = modelPojoToUiHolder.methodName;
						if (methodName != null) {
							obj = ExpressionMethodHandler.getValue(methodName, obj, childWidget);
						}

						((BaseWidget) childWidget).applyStyleToWidget(attrHolder.widgetAttribute, obj);
						int updateUiFlag = attrHolder.widgetAttribute.getUpdateUiFlag();
						if ((updateUiFlag & UPDATE_UI_REQUEST_LAYOUT) != 0) {
							childWidget.requestLayout();
						}
						
						if ((updateUiFlag & UPDATE_UI_INVALIDATE) != 0) {
							childWidget.invalidate();
						}
					}
				}
			} finally {
				fragment.enableRemeasure();
			}
		}
	}

	protected void invalidateChildIfRequired(IWidget childWidget) {
		
	}
}
