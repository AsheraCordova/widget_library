//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package com.ashera.attributedtext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ashera.core.IFragment;
import com.ashera.widget.PluginInvoker;
import com.ashera.widget.WidgetAttribute;
import com.ashera.widget.WidgetAttributeMap;
import com.ashera.widget.WidgetAttributeValue;

public class AugmentedIntervalTree {

    private Interval root;
    private String text;    
	private Set<String> list = new HashSet<>();
	private IFragment fragment;
	private Map<String, Object> htmlConfig;
	public AugmentedIntervalTree(Map<String, Object> htmlConfig, IFragment fragment) {
		this.fragment = fragment;
		this.htmlConfig = htmlConfig;
		
		if (this.htmlConfig == null) {
			this.htmlConfig = new HashMap<>(0);
		}
		
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

    public void insertNode(Interval newNode) {
    	String key = newNode.getStart() + "_" + newNode.getEnd();
    	if (!list.contains(key)) {
	    	list.add(key); 
	    	if (root == null) {
	    		root = newNode;
	    		return;
	    	}
	    	this.root = insertNode(this.root, newNode);
    	}
    }
    public Interval insertNode(Interval tmp, Interval newNode) {
        if (tmp == null) {
            tmp = newNode;
            return tmp;
        }
        if (newNode.getEnd() > tmp.getMax()) {
            tmp.setMax(newNode.getEnd());
        }

        if (tmp.compareTo(newNode) <= 0) {

            if (tmp.getRight() == null) {
            	if (newNode.getStart() == tmp.getStart() && newNode.getEnd() >= tmp.getEnd()) {
            		newNode.setStart(tmp.getEnd());
            		insertNode(this.root, newNode);
            	} else {
            		tmp.setRight(newNode);
            	}
            }
            else {
            	if (newNode.getStart() == tmp.getStart() && newNode.getEnd() >= tmp.getEnd()) {
            		newNode.setStart(tmp.getEnd());            		
            	}
                insertNode(tmp.getRight(), newNode);
            }
        }
        else {
            if (tmp.getLeft() == null) {
            	if (newNode.getEnd() > tmp.getStart()) {
            		int newNodeEnd = newNode.getEnd();
            		newNode.setEnd(tmp.getStart());
            		
            		if (newNodeEnd > tmp.getEnd()) {
            			insertNode(this.root, newNode.makeNode(tmp.getEnd(), newNodeEnd,
            					newNode.getArgs()));
            		}
            	}
                tmp.setLeft(newNode);
            }
            else {
                insertNode(tmp.getLeft(), newNode);
            }
        }
        return tmp;
    }

    public void printTree() {
    	printTree(this.root);
    }
    public void printTree(Interval tmp) {
        if (tmp == null) {
            return;
        }

        if (tmp.getLeft() != null) {
            printTree(tmp.getLeft());
        }

        System.out.println(tmp);

        if (tmp.getRight() != null) {
            printTree(tmp.getRight());
        }
    }
    
    public void intersectInterval(Interval tmp, Interval i, List<Interval> res) {

        if (tmp == null) {
            return;
        }

        if (!((tmp.getStart() > i.getEnd()) || (tmp.getEnd() < i.getStart()))) {
            if (res == null) {
                res = new ArrayList<Interval>();
            }
            res.add(tmp);
        }

        if ((tmp.getLeft() != null) && (tmp.getLeft().getMax() >= i.getStart())) {
            this.intersectInterval(tmp.getLeft(), i, res);
        }

        this.intersectInterval(tmp.getRight(), i, res);
    }

	public void apply(AttributedString spannableString) {
		apply(this.root, spannableString);
	}
	public void apply(Interval tmp, AttributedString spannableString) {
        if (tmp == null) {
            return;
        }

        if (tmp.getLeft() != null) {
        	apply(tmp.getLeft(), spannableString);
        }

        int start = tmp.getStart();
        int end = tmp.getEnd();
        spannableString.init(start, end);
        
        int style = 0;
        float textSize = 0f;
        Object typeFace = null;

        if (tmp instanceof StyleInterval) {
	        Map<String, String> attributes = getStyle(((StyleInterval) tmp).getStyle());
	        String verticalAlign = null;
	        Set<String> widgetAttributes = attributes.keySet();
			for (String widgetAttribute : widgetAttributes) {
				String value = getValue(widgetAttribute, attributes);
				
				if (value != null) {
					switch (widgetAttribute) {
					// color attributes
					case "color":
					case "textColor":{
						Object objcolor = quickConvert("color", value);
			        	spannableString.applyColor(objcolor, start, end);
					}
					break;
					
					case "background-color":
					case "background":{
			        	Object objcolor = quickConvert("color", value);
			        	spannableString.applyBackgroundColor(objcolor, start, end);
					}
			        break;
					
			        // text alignment
					case "textAlignment": {
						String textAlign = value;
						String align = "left";
						switch (textAlign) {
						case "center":
							align = "center";
							break;
						case "textEnd":
						case "viewEnd":
							align = "right";
							break;
						case "textStart":
						case "viewStart":
							align = "left";
							break;	
						default:
							break;
						}
						spannableString.applyTextAlign(align, start, end);
					}
					break;
					case "gravity": {
						String gravity = value + "|";
	
						String align = "left";
						if (gravity.contains("center|") || gravity.contains("center_horizontal|")) {
							align = "center";
						}
						
						if (gravity.contains("right|") || gravity.contains("end|")) {
							align = "right";
						}
						
						spannableString.applyTextAlign(align, start, end);	
					}
					break;
					case "text-align": {
						spannableString.applyTextAlign(value, start, end);
					}
			        break;

					case "vertical-align": {
						verticalAlign = value;
					}
					break;
			        
					// apply font attributes
					case "font-family":
					case "fontFamily":{
						typeFace = quickConvert("font", value);
					}
					break;
					
					case "font-size":
					case "textSize":{
						textSize = (Float) quickConvert("dimensionsp", value); 
					}
					break;
					case "textStyle": {
						String textStyle = value + "|";
						if (textStyle.contains("bold|")) {
							style |= 1;
						}
						
						if (textStyle.contains("italics|")) {
							style |= 2;
						}
					}
					break;
					case "font-weight": {
			        	int weight = 0;
			        	if (value.equals("bold")) {
			        		weight = 2;
			        	}
						style |= weight;
			        }
					break;
					
					case "font-style": {
						int fontStyle = 0;
			        	if (value.equals("italics")) {
			        		fontStyle = 2;
			        	}
						style |= fontStyle;
			        }
					break;
					
					// others
					case "text-decoration": {
						if (value.equals("underline")) {
			        		spannableString.applyUnderLine(start, end);
			        	} else if (value.equals("line-through")) {
							spannableString.applyStrikeThrough(start, end);
			        	}
					}
					break;

					case "line-height": 
					case "lineHeight": {
			        	Float lineHeight = (Float) quickConvert("dimensionfloat", value);
			        	spannableString.applyLineHeight(lineHeight.floatValue(), start, end);
					}
			        break;
					default:
						break;
					}
				}
			}

	        if (typeFace != null || style != 0 || textSize != 0) {
	        	spannableString.applyFont(typeFace, textSize, style, start, end);
	        }
	        
	        if (verticalAlign != null) {
	        	spannableString.applyVerticalAlign(verticalAlign, start, end);
	        }

        } 
        
        if (tmp instanceof UrlInterval) {
        	spannableString.applyUrl(((UrlInterval) tmp).getHref(), htmlConfig.get("textColorLink"), start, end);
        }
        
        if (tmp instanceof ImageInterval) {
        	Object image = quickConvert("drawable", ((ImageInterval) tmp).getSrc());
        	spannableString.applyImg(image, start, end);
        }
        
        if (tmp instanceof BulletInterval) {
        	BulletInterval bulletInterval = (BulletInterval) tmp;
			spannableString.applyBullet(bulletInterval.getIndent(), bulletInterval.getBulletSpacing(), start, end);
        }
        
        if (tmp.getRight() != null) {
        	apply(tmp.getRight(), spannableString);
        }
    }
	private Object quickConvert(String name, String value) {
		Object objcolor = PluginInvoker.convertFrom(PluginInvoker.getConverter(name), null, value, fragment);
		return objcolor;
	}
	
	private String getValue(String key, Map<String, String> attributes) {
		String os = PluginInvoker.getOS().toLowerCase();
		if (attributes.containsKey(key + "-" + os)) {
			return attributes.get(key + "-" + os);
		}
		
		return attributes.get(key);
	}
	
	public Map<String, String> getStyle(WidgetAttributeMap inlineStyle) {
		Map<String, String> attributes = new HashMap<>();
		updateStyleInMap(inlineStyle, attributes);
	    return attributes;
	}
	private void updateStyleInMap(WidgetAttributeMap inlineStyle,
			Map<String, String> attributes) {
		WidgetAttributeMap parent = inlineStyle.getParent();
		while (parent != null) {			
			updateStyleInMap(parent, attributes);
			parent = parent.getParent();
		}

		Set<WidgetAttribute> keys = inlineStyle.keySet();
		for (WidgetAttribute widgetAttribute : keys) {
			List<WidgetAttributeValue> values = inlineStyle.get(widgetAttribute);
			for (WidgetAttributeValue widgetAttributeValue : values) {
				attributes.put(widgetAttribute.getAttributeName(), widgetAttributeValue.getValue());
			}
		}
	}

}