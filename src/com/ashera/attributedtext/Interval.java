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


public abstract class Interval implements Comparable<Interval> {

    private int     start;
    private int     end;
    private int     max;
    private Interval left;
    private Interval right;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public Interval getLeft() {
		return left;
	}

	public void setLeft(Interval left) {
		this.left = left;
	}

	public Interval getRight() {
		return right;
	}

	public void setRight(Interval right) {
		this.right = right;
	}

	public Interval(int start, int end) {
        this.start = start;
        this.end = end;
        this.max = end;
    }
 
    @Override
    public int compareTo(Interval i) {
        if (this.start < i.start) {
            return -1;
        }
        else if (this.start == i.start) {
            return this.end <= i.end ? -1 : 1;
        }
        else {
            return 1;
        }
    }
    
    public abstract Interval makeNode(int start, int end, Object... arguments);
    public abstract Object[] getArgs(); 

	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
