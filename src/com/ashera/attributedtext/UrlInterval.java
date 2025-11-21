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


public class UrlInterval extends StyleInterval {
    private String href;
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	
	public UrlInterval(int start, int end, WidgetAttributeMap style, String href) {
		super(start, end, style);
        this.href = href;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.href + "]";
    }

	@Override
	public Interval makeNode(int start, int end, Object... arguments) {		
		return new UrlInterval(start, end, (WidgetAttributeMap) arguments[0], (String) arguments[1]);
	}

	@Override
	public Object[] getArgs() {
		return new Object[] {getStyle(), href};
		
	}


	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
