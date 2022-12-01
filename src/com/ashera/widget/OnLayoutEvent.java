package com.ashera.widget;

public class OnLayoutEvent extends WidgetEvent{
	private boolean changed;
	private int l;
	private int t; 
	private int r;
	private int b;
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}

	
}
