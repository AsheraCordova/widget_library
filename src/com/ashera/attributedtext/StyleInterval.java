package com.ashera.attributedtext;

import com.ashera.widget.WidgetAttributeMap;


public class StyleInterval extends Interval {
    private WidgetAttributeMap style;
	public WidgetAttributeMap getStyle() {
		return style;
	}

	public void setStyle(WidgetAttributeMap style) {
		this.style = style;
	}

	
	public StyleInterval(int start, int end, WidgetAttributeMap style) {
		super(start, end);
        this.style = style;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.style + "]";
    }

	@Override
	public Interval makeNode(int start, int end, Object... arguments) {		
		return new StyleInterval(start, end, (WidgetAttributeMap) arguments[0]);
	}

	@Override
	public Object[] getArgs() {
		return new Object[] {style};
		
	}


	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
