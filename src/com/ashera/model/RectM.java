package com.ashera.model;

public class RectM {

	/**
	 * the x coordinate of the rectangle
	 */
	public int x;

	/**
	 * the y coordinate of the rectangle
	 */
	public int y;

	/**
	 * the width of the rectangle
	 */
	public int width;

	/**
	 * the height of the rectangle
	 */
	public int height;

	/**
	 * Construct a new instance of this class given the
	 * x, y, width and height values.
	 *
	 * @param x the x coordinate of the origin of the rectangle
	 * @param y the y coordinate of the origin of the rectangle
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public RectM (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}