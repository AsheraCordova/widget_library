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


public class BulletInterval extends StyleInterval {
    private int indent;
    private int bulletSpacing;
	public int getBulletSpacing() {
		return bulletSpacing;
	}

	public void setBulletSpacing(int bulletSpacing) {
		this.bulletSpacing = bulletSpacing;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	
	public BulletInterval(int start, int end, WidgetAttributeMap style, int indent, int bulletSpacing) {
		super(start, end, style);
        this.indent = indent;
        this.bulletSpacing = bulletSpacing;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.indent + "]";
    }

	@Override
	public Interval makeNode(int start, int end, Object... arguments) {		
		return new BulletInterval(start, end, (WidgetAttributeMap) arguments[0], (Integer) arguments[1], (Integer) arguments[2]);
	}

	@Override
	public Object[] getArgs() {
		return new Object[] { getStyle(), indent, bulletSpacing};
		
	}


	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
