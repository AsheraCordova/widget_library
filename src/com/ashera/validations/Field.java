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
package com.ashera.validations;


import java.util.LinkedList;
import java.util.List;


/**
 * Field class
 */
public class Field {
    public final static int DEFAULT_ORDER = 10;
    private @com.google.j2objc.annotations.Weak final FormElement mViewWrapper;
    private final List<Validation> mValidations;
    private Object metaData;

    public int getOrder() {
        return mOrder;
    }

    private int mOrder = 0;
    public static Field using(final FormElement viewWrapper, int order) {
        if (viewWrapper == null) {
            throw new IllegalArgumentException("EditText field may not be null");
        }
        return new Field(viewWrapper, order);
    }

    private Field(final FormElement viewWrapper, int order) {
        mOrder = order;
        mViewWrapper = viewWrapper;
        mValidations = new LinkedList<Validation>();
    }

    public Field addValidator(final Validation what) {
        mValidations.add(what);
        return this;
    }

    public FormElement getViewWrapper() {
        return mViewWrapper;
    }

    public boolean isValid() throws FieldValidationException {
        if (mViewWrapper.isViewVisible()) {
            for (final Validation validation : mValidations) {
                if (!validation.isValid(mViewWrapper.getTextEntered(), mViewWrapper)) {
                    final String errorMessage = validation.getErrorMessage(mViewWrapper);
                    throw new FieldValidationException(errorMessage, mViewWrapper);
                }
            }
        }

        return true;
    }

    public void setMetaData(Object metaData) {
        this.metaData = metaData;
    }
}
