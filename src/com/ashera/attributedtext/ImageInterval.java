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


public class ImageInterval extends Interval {
    private String src;
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	
	public ImageInterval(int start, int end, String src) {
		super(start, end);
        this.src = src;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.src + "]";
    }

	@Override
	public Interval makeNode(int start, int end, Object... arguments) {		
		return new ImageInterval(start, end, (String) arguments[0]);
	}

	@Override
	public Object[] getArgs() {
		return new Object[] { src };
		
	}


	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
