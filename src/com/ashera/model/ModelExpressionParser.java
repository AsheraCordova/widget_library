package com.ashera.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelExpressionParser {
    private static final Pattern VAR_REGEX = Pattern.compile(
            "let\\s*(\\w+)\\s*=\\s*([\\w\\.\\[\\]]+)\\s*from\\s*([\\w#]+)\\s*\\->(\\w+)\\s*into\\s*(\\w+)\\s*as\\s*(\\w+)\\s*");
    private static final Pattern LOOP_REGEX = Pattern.compile(
            "let\\s*(\\w+)\\s*in\\s*([\\w\\.\\[\\]]+)\\s*from\\s*([\\w#]+)\\s*\\->(\\w+)\\s*into\\s*(\\w+)\\s*as\\s*(\\w+)\\s*");
    private static final Pattern UISET_FROMMODEL_REGEX = Pattern
            .compile("\\s*(\\w+)\\s*=\\s*(\\w*\\()?([\\w\\.\\[\\]]+)\\)?\\s*from\\s*([\\w#]+)\\s*\\->(\\w+)\\s*");
    private static final Pattern MODELSET_FROMUI_REGEX = Pattern
            .compile("\\s*([\\w\\.]+)\\s*=\\s*([\\w\\.\\[\\]]+)\\s*into\\s*([\\w#]+)\\s*\\->(\\w+)\\s*(as\\s*(\\w+)\\s*)?");
    private static final Pattern EVENT_VAR_EXPRESSION = Pattern
            .compile("\\s*(\\w+)\\s*=\\s*(\\w*\\{)?([\\w\\.\\[\\]]+)\\}?\\s*from\\s*([\\w#]+)\\s*\\->(\\w+)\\s*");
    private static final Pattern MODEL_FROM_SCOPE_EXPRESSION = Pattern
            .compile("\\s*([\\w\\.\\[\\]]+)\\s*from\\s*([\\w#]+)\\s*\\->(\\w+)\\s*");
    private static final Pattern MODEL_UPDATE_TO_SCOPE_EXPRESSION = Pattern
            .compile("\\s*([\\w\\.\\[\\]]+)\\s*into\\s*([\\w#]+)\\s*\\->(\\w+)\\s*(as\\s*(\\w+)\\s*)?");
    private static final Pattern VAR_SET_STORE_REGEX = Pattern.compile("\\s*([\\w#]+)\\s*\\->(\\w+)\\s*as\\s*(\\w+)\\s*");

    public static class ModelEventHolder {
        public String varName;
        public String varPath;
        public String key;
        public ModelScope varScope;
        public String methodName;

        public ModelEventHolder(List<String> data) {
            this.key = data.get(0);
            this.methodName = data.get(1);
            if (methodName != null) {
            	methodName = methodName.replaceAll("\\{", "");
            }

            this.varPath = data.get(2);
            this.varName = data.get(3);
            this.varScope = ModelScope.valueOf(data.get(4));
        }
    }


    public static List<ModelEventHolder> parseEventExpression(String expression) {
        String[] varParams = expression.split(",");
        List<ModelEventHolder> result = new ArrayList<>();
        for (String varParam : varParams) {
            result.add(new ModelEventHolder(
                evelRegEx(varParam, EVENT_VAR_EXPRESSION, "Invalid expression. e.g. y=z from z->view")));
        }
        
        return result;
    }
    
    public static class ModelFromScopeHolder {
        public String varName;
        public String varPath;
        public ModelScope varScope;


        public ModelFromScopeHolder(List<String> data) {
            this.varPath = data.get(0);
            this.varName = data.get(1);
            this.varScope = ModelScope.valueOf(data.get(2));
        }
    }
    
    public static ModelFromScopeHolder parseModelFromScope(String expression) {
        return new ModelFromScopeHolder(
                evelRegEx(expression, MODEL_FROM_SCOPE_EXPRESSION, "Invalid expression. e.g. z from z->view"));
    }

    public static class ModelPojoToUiHolder {
        public String varPath;
        public String key;
        public String methodName;
        public ModelScope varScope;
        public String modelAttribute;

        public ModelPojoToUiHolder(List<String> data) {
            this.modelAttribute = data.get(0);
            this.methodName = data.get(1);
            if (methodName != null) {
            	methodName = methodName.replaceAll("\\(", "");
            }
            this.varPath = data.get(2);
            this.key = data.get(3);
            this.varScope = ModelScope.valueOf(data.get(4));
        }
    }

    public static List<ModelPojoToUiHolder> parsePojoToUiExpression(String expression) {
        String[] varParams = expression.split(";");
        List<ModelPojoToUiHolder> result = new ArrayList<>();
        for (String varParam : varParams) {
            result.add(new ModelPojoToUiHolder(
                evelRegEx(varParam, UISET_FROMMODEL_REGEX, "Invalid expression. e.g. text=abcd from x->view")));
        }
        
        return result;
    }

    public static class ModelUiToPojoHolder {
        public String varPath;
        public String key;
        public ModelScope varScope;
        public String modelAttribute;
        public String varName;
        public ModelDataType varType;

        public ModelUiToPojoHolder(List<String> data) {
            this.varPath = data.get(0);
            this.modelAttribute = data.get(1);
            this.varName = data.get(2);
            this.varScope = ModelScope.valueOf(data.get(3));
            
            String strVarType = data.get(5);
            if (strVarType != null) {
            	this.varType = ModelDataType.valueOf(strVarType);
            }
        }
    }

    public static List<ModelUiToPojoHolder> parseUiToPojoExpression(String expression) {
        String[] varParams = expression.split(";");
        List<ModelUiToPojoHolder> result = new ArrayList<>();
        for (String varParam : varParams) {
            result.add(new ModelUiToPojoHolder(evelRegEx(varParam, MODELSET_FROMUI_REGEX, "Invalid expression. e.g. abcd=text into x->view as boolean")));
        }
        
        return result;
    }

    public static class ModelStoreVarHolder {
        public String varName;
        public ModelScope varScope;
        public ModelDataType varType;

        public ModelStoreVarHolder(List<String> data) {
            this.varName = data.get(0);
            this.varScope = ModelScope.valueOf(data.get(1));
            
            String strVarType = data.get(2);
            if (strVarType != null) {
            	this.varType = ModelDataType.valueOf(strVarType);
            }
        }

    }
    public static ModelStoreVarHolder parseModelStoreVarExpression(String expression) {
        return new ModelStoreVarHolder(evelRegEx(expression, VAR_SET_STORE_REGEX, "Invalid expression. e.g. y->session as pathmap"));
    }

    public static class ModelVarHolder {
        public String varName;
        public String varPath;
        public String key;
        public ModelScope varSource;
        public ModelScope varScope;
        public ModelDataType dataType;


        public ModelVarHolder(List<String> data) {
            this.varName = data.get(0);
            this.varPath = data.get(1);
            this.key = data.get(2);
            this.varSource = ModelScope.valueOf(data.get(3));
            this.varScope = ModelScope.valueOf(data.get(4));
            this.dataType = ModelDataType.valueOf(data.get(5));
        }
    }


    public static List<ModelVarHolder> parseModelVarExpression(String expression) {
        String[] varParams = expression.split(";");
        List<ModelVarHolder> result = new ArrayList<>();
        for (String varParam : varParams) {
            result.add(new ModelVarHolder(evelRegEx(varParam, VAR_REGEX,
                    "Invalid expression. e.g. let x = . from y->view into session as pathmap")));
        }
        return result;
    }

    public static class ModelLoopHolder {
        public String varName;
        public String varPath;
        public String key;
        public ModelScope varSource;
        public ModelScope varScope;
        public ModelDataType dataType;


        public ModelLoopHolder(List<String> data) {
            this.varName = data.get(0);
            this.varPath = data.get(1);
            this.key = data.get(2);
            this.varSource = ModelScope.valueOf(data.get(3));
            this.varScope = ModelScope.valueOf(data.get(4));
            this.dataType = ModelDataType.valueOf(data.get(5));
        }
    }
    
    public static ModelLoopHolder parseModelLoopExpression(String expression) {
        return new ModelLoopHolder(evelRegEx(expression, LOOP_REGEX,
                "Invalid expression. e.g. let x in y[0] from y->view into session as pathmap"));
    }


    private static List<String> evelRegEx(String expression, Pattern regEx, String message) {
        Matcher m = regEx.matcher(expression);
        boolean b = m.matches();
        List<String> groups = new ArrayList<>();
        if (b) {
            for (int i = 1; i <= m.groupCount(); i++) {
                groups.add(m.group(i));

            }
        } else {
            throw new RuntimeException(message + " : " + expression);
        }
        return groups;
    }


    public static class ModelUpdateToScopeHolder {
        public String varName;
        public String varPath;
        public ModelDataType varType;
        public ModelScope varScope;


        public ModelUpdateToScopeHolder(List<String> data) {
            this.varPath = data.get(0);
            this.varName = data.get(1);
            this.varScope = ModelScope.valueOf(data.get(2));
            this.varType = ModelDataType.valueOf(data.get(4));
        }
    }
    
    public static ModelUpdateToScopeHolder parseModelUpdateToScope(String expression) {
        return new ModelUpdateToScopeHolder(
                evelRegEx(expression, MODEL_UPDATE_TO_SCOPE_EXPRESSION, "Invalid expression. e.g. flag into map->view as boolean"));
    }
    

    public static Map<String, Object> parseSimpleCssExpression(String cssStr) {
    	Map<String, Object> params = new java.util.HashMap<>();
		return parseSimpleCssExpression(cssStr, params);
	}

    public static Map<String, Object> parseSimpleCssExpression(String cssStr, Map<String, Object> params) {
		if (cssStr != null && !cssStr.isEmpty()) {
			String[] attrs = cssStr.split(";");
			for (String attr : attrs) {
				attr = attr.trim();
				String[] nameAndValue = attr.split("\\:");
				String key = nameAndValue[0];
				String value = nameAndValue.length <= 1 ? "" : nameAndValue[1];
				key = key.trim();
				value = value.trim();
				params.put(key, value);
			}
		}
		return params;
	}
    public static void main(String[] args) {
        System.out.println(ModelExpressionParser
                .parseModelVarExpression("let x = . from y#test1->view into session as pathmap").get(0).varName);
    }
}
