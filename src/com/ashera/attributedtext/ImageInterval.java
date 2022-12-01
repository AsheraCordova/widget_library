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
