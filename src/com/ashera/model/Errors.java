package com.ashera.model;

import java.util.ArrayList;
import java.util.List;

public class Errors {
    private List<Error> errors = new ArrayList<>();
    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
    
    public boolean hasErrors() {
        return errors.size() > 0; 
    }

    public void addError(com.ashera.model.Error error) {
    	errors.add(error);
    }

	public void clear() {
		errors.clear();
	}
}
