package com.ashera.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.ashera.widget.BaseWidget;
import com.ashera.widget.IWidget;
import com.ashera.widget.WidgetAttribute;

public class Error {
    private String path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    private String description;
    private String stackTrace;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStackTrace() {
        return stackTrace;
    }
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    public Error() {
	}
    
    public Error(WidgetAttribute widgetAttribute, IWidget widget, Exception e) {
        StringBuilder stringBuilder = new StringBuilder(widget.getLocalName());
        
        if (widget.getId() != null) {
            stringBuilder.append("[").append(widget.getId()).append("]");
        }
        if (widgetAttribute != null) {
        	stringBuilder.append("/@").append(widgetAttribute.getAttributeName());
        }
        path = stringBuilder.toString();
        description = e.getMessage();
        
        if (e.getMessage() == null) {
        	description = e.getClass().getName();
        }
        
        StringWriter stringWriter = null;
        PrintWriter p = null;
        try {
			stringWriter = new StringWriter();
			p = new PrintWriter(stringWriter);
			e.printStackTrace(p);
			stackTrace = stringWriter.toString();
		} finally {
			try {
				stringWriter.close();
			} catch (IOException e1) {
			}
			p.close();
		}
    }
    
	public Error(Map<String, Object> map, BaseWidget widget, Exception e) {
		 StringBuilder stringBuilder = new StringBuilder(widget.getLocalName());
	        if (map != null) {
	        	stringBuilder.append(map);
	        }
	        path = stringBuilder.toString();
	        description = e.getMessage();
	        
	        if (e.getMessage() == null) {
	        	description = e.getClass().getName();
	        }
	        
	        StringWriter stringWriter = new StringWriter();
	        e.printStackTrace(new PrintWriter(stringWriter));
	        stackTrace = stringWriter.toString();
		
	}
}
