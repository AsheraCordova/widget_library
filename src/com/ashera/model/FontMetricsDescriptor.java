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
package com.ashera.model;

public class FontMetricsDescriptor {
	/**
     * The maximum distance above the baseline for the tallest glyph in
     * the font at a given text size.
     */
    public int   top;
    /**
     * The recommended distance above the baseline for singled spaced text.
     */
    public int   ascent;
    /**
     * The recommended distance below the baseline for singled spaced text.
     */
    public int   descent;
    /**
     * The maximum distance below the baseline for the lowest glyph in
     * the font at a given text size.
     */
    public int   bottom;
    /**
     * The recommended additional space to add between lines of text.
     */
    public int   leading;
}
