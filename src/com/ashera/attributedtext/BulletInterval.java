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
