package com.ashera.validations;

import com.ashera.widget.IWidget;

public abstract class BaseValidator implements Validation {
	protected String errorMessage;
	@Override
	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getErrorMessage(IWidget widget) {
		if (errorMessage == null) {
			return getDefaultErrorMessage(widget);
		}
		return errorMessage;
	}

	protected String getDefaultErrorMessage(IWidget widget) {
		return null;
	}
}
