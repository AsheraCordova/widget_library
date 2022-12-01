package com.ashera.attributedtext;

import com.ashera.widget.WidgetAttributeMap;


public class UrlInterval extends StyleInterval {
    private String href;
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	
	public UrlInterval(int start, int end, WidgetAttributeMap style, String href) {
		super(start, end, style);
        this.href = href;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.href + "]";
    }

	@Override
	public Interval makeNode(int start, int end, Object... arguments) {		
		return new UrlInterval(start, end, (WidgetAttributeMap) arguments[0], (String) arguments[1]);
	}

	@Override
	public Object[] getArgs() {
		return new Object[] {getStyle(), href};
		
	}


	// GETTERS E SETTERS OMITTED FOR SIMPLICITY
	
}
