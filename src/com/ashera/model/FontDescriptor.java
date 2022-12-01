package com.ashera.model;

public class FontDescriptor {
    private String name;
    private int style;
    
    public FontDescriptor(String name, int style) {
        super();
        this.name = name;
        this.style = style;
    }
    
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
