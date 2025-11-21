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

import com.ashera.widget.WidgetAttributeMap;


public class StyleInterval extends Interval {
    private WidgetAttributeMap style;
	public WidgetAttributeMap getStyle() {
		return style;
	}

	public void setStyle(WidgetAttributeMap style) {
		this.style = style;
	}

	
	public StyleInterval(int start, int end, WidgetAttributeMap style) {
		super(start, end);
        this.style = style;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.style + "]";
    }

	@Override
	public Interval makeNode(int start, int end, Object... arguments) {		
		return new StyleInterval(start, end, (WidgetAttributeMap) arguments[0]);
	}

	@Override
	public Object[] getArgs() {
		return new Object[] {style};
		
	}


	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
