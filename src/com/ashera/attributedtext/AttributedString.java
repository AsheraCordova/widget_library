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

public interface AttributedString {
	Object get();
	void init(int start, int end);
	void applyUnderLine(int start, int end);
	void applyColor(Object color, int start, int end);
	void applyStrikeThrough(int start, int end);
	void applyFont(Object typeFace, float textSize, int style, int start, int end);
	void applyLineHeight(float floatValue, int start, int end);
	void applyTextAlign(String value, int start, int end);
	void applyBackgroundColor(Object objcolor, int start, int end);
	void applyVerticalAlign(String verticalAlign, int start, int end);
	void applyUrl(String href, Object linkColor, int start, int end);
	void applyImg(Object image, int start, int end);
	void applyBullet(int indent, int spacing, int start, int end);
	String getText(); 
}
