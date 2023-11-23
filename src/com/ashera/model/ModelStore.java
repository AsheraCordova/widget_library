package com.ashera.model;

import java.util.Map;

import com.ashera.core.IFragment;
import com.ashera.widget.IWidget;
import com.ashera.widget.PluginInvoker;

public class ModelStore {
	public static void storeModelToScope(String varName, ModelScope varScope, Object objValue, IFragment fragment, IWidget widget, LoopParam loopVarParams) {
        switch (varScope) {
            case view:
            	fragment.storeUserData(varName, objValue);
            break;
        	case session:        		
        		fragment.getRootActivity().storeUserData(varName, objValue);
        		break;
            case local:
            	if (widget != null) {
            		widget.storeUserData(varName, objValue);
            	} else {
	        		throw new RuntimeException("local scope not supported");
	        	}
                break;
            case component:
            	if (varName.contains("#")) {
            		fragment.storeUserData(varName, objValue);
            	} else {
	            	if (widget.getComponentId() != null) {
	            		fragment.storeUserData(widget.getComponentId() + "#" + varName, objValue);
	            	}
            	}
            	
            	
            	break;
            case loopvar:
            	if (objValue == loopVarParams) {
            		//do nothing
            	} else if (loopVarParams != null) {
            		loopVarParams.put(varName, objValue);
            	} else {
            		throw new RuntimeException("loopvar scope not supported");
            	}
            	break;
        	default:
        		throw new RuntimeException("unsupported scope " + varScope);
        }
    }

	public static Object getModelFromScope(String varName, ModelScope varScope,IFragment fragment, IWidget widget, Map<String, Object> loopVarParams) {
        Object obj = null;
		switch (varScope) {
		    case view:
		        obj = fragment.getUserData(varName);
		        break;
			case session:
				obj = fragment.getRootActivity().getUserData(varName);
				break;
			case component:
				if (widget.getComponentId() != null) {
					if (varName.contains("#")) {
						obj = fragment.getUserData(varName);
					} else {
						obj = fragment.getUserData(widget.getComponentId() + "#" + varName);
					}
				}
				break;
	        case local:
	        	if (widget != null) {
	        		obj = widget.getUserData(varName);
	        	} else {
	        		throw new RuntimeException("local scope not supported");
	        	}
	            break;
	        case loopvar:
            	if (loopVarParams != null) {
            		obj = loopVarParams.get(varName);
            	} else {
            		//throw new RuntimeException("loopvar scope not supported");
            	}
            	break;	
            case strings:
            	return com.ashera.utils.ResourceBundleUtils.getString("values/strings", "string", "@string/" + varName, fragment);
            case constants:
            	return varName;
			default:
				throw new RuntimeException("unsupported scope " + varScope);
		}
        return obj;
    }
    

    public static Object changeModelDataType(ModelDataType dataType, Object obj) {
    	if (dataType != null) {
        switch (dataType) {
	        case map:
	            if (obj instanceof IFlatMap) {
	                obj = ((IFlatMap) obj).getHierarchicalMap();
	            } else {
	                obj = PluginInvoker.getMap(obj);
	            }
	            break;
	        case pathmap:
	            if (!(obj instanceof IFlatMap)) {
	                obj = new com.ashera.model.PlainMap(PluginInvoker.getMap(obj));
	            }
	            break;
	        case list:
	            obj = PluginInvoker.getList(obj);
	            break;
	        case string:
	            obj = PluginInvoker.getString(obj);
	            break;
	        case bool:
	            obj = PluginInvoker.getBoolean(obj);
	            break;
	        case number:
	            obj = PluginInvoker.getDouble(obj);
	            break;
	        case integer:
	            obj = PluginInvoker.getInt(obj);
	            break;
	        default:
	            break;
	        }
    	}
        return obj;
    }
}
