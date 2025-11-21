//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
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
