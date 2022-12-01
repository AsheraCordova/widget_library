/*
 * Copyright 2013 Philip Schiffer
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *  This file incorporates code covered by the following terms:
 *
 *     The MIT License (MIT)
 *
 *     Copyright (c) 2013 Vitaliy Zasadnyy
 *
 *     Permission is hereby granted, free of charge, to any person obtaining a copy of
 *     this software and associated documentation files (the "Software"), to deal in
 *     the Software without restriction, including without limitation the rights to
 *     use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *     the Software, and to permit persons to whom the Software is furnished to do so,
 *     subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in all
 *     copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *     FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *     COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *     IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *     CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.ashera.validations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ashera.widget.IWidget;


/**
 * form class
 */
public class Form {

    private final List<Field> mFields;
    private boolean requireOrdering = false;
    private HashMap<String, IWidget> widgetMap = new HashMap<>();

    public Form() {
        mFields = new ArrayList<>();
    }

    public void addField(final Field field) {
        if (!requireOrdering && field.getOrder() != Field.DEFAULT_ORDER) {
            requireOrdering = true;
        }
        widgetMap.put(field.getViewWrapper().getId(), field.getViewWrapper());
        mFields.add(field);
    }


    /**
     * check if all the fields in form is valid.
     * @return true or false
     */
    public boolean isValid() {
        if (requireOrdering) {
            Collections.sort(mFields, new Comparator<Field>() {
                @Override
                public int compare(Field lhs, Field rhs) {
                    return new Integer(lhs.getOrder()).compareTo(rhs.getOrder());
                }
            });
        }
        boolean result = true;
        boolean focus = false;
        resetErrors();
        for (final Field field : mFields) {
            try {
                result &= field.isValid();
            } catch (final FieldValidationException e) {
                result = false;
                showErrorMessage(e);

                if (!focus) {
                    final FormElement viewWrapper = e.getViewWrapper();
                    viewWrapper.focus();
                    focus = true;
                }
            }
        }

        return result;
    }

    /**
     * method to reset all errors in form
     */
    public void resetErrors() {
        for (final Field field : mFields) {
            field.getViewWrapper().resetError();
        }
    }

    //
    public Map<String, String> getFormElementValues() {
    	HashMap<String, String> formElementsMap = new HashMap<String, String>();
    	for (final Field field : mFields) {
    		FormElement viewWrapper = field.getViewWrapper();
    		if (viewWrapper.getId() != null) {
    			formElementsMap.put(viewWrapper.getId(), viewWrapper.getTextEntered());
    		}
    	}
    	
    	return formElementsMap;
    }
    /**
     * @param e Exception
     */
    protected void showErrorMessage(final FieldValidationException e) {
        e.getViewWrapper().showError(e.getMessage());
    }
}
