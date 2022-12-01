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
