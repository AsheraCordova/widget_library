package com.ashera.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventExpressionParser {
    public static final String KEY_EVENT_ARGS = "eventArgs";
    public static final String KEY_SCRIPT_NAME = "scriptName";
    public static final String KEY_COMMAND_NAME = "commandName";
    public static final String KEY_COMMAND_TYPE = "commandType";
    public static final String KEY_EVENT = "event";
	private static final Pattern EVENT_REGEX = Pattern.compile(
            "(\\w*)(\\+|\\:)?(\\w*)#?(\\w*)?\\(?([^)]*)\\)?");


    public static Map<String, Object> parseEventExpression(String expression, Map<String, Object> eventMap) {
    	List<String> eventArr = evelRegEx(expression, EVENT_REGEX, "Invalid expression. e.g. onClick+freemarket#test(...)");
		String javascriptEvent = eventArr.get(0);
    	eventMap.put(KEY_EVENT, javascriptEvent);
		putValueToMap(eventMap, KEY_COMMAND_TYPE,  eventArr.get(1), "");
		putValueToMap(eventMap, KEY_COMMAND_NAME, eventArr.get(2), "java");
		putValueToMap(eventMap, KEY_SCRIPT_NAME, eventArr.get(3), javascriptEvent);
    	putValueToMap(eventMap, KEY_EVENT_ARGS, eventArr.get(4), "");
    	
        return eventMap;
    }

	private static void putValueToMap(Map<String, Object> eventMap, String key, String value, String defaultValue) {
		eventMap.put(key, value == null || value.isEmpty() ?  defaultValue : value);
	}
    
    public static Map<String, Object> parseEventExpression(String expression) {
    	Map<String, Object> eventMap = new HashMap<String, Object>();
        return parseEventExpression(expression, eventMap);
    }


    public static void main(String[] args) {
        System.out.println(EventExpressionParser
                .parseEventExpression("setXml(xml = getFileAsset{.} from xml->view)"));
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
}
