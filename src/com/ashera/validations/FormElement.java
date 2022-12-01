package com.ashera.validations;

import java.util.List;

import com.ashera.widget.IWidget;


/**
 * Wrapper interface for views
 */
public interface FormElement extends IWidget {	
	String getFormGroupId();
    String getTextEntered();
    boolean isViewVisible();
    void focus();
	void resetError();
	void showError(String message);
	void setCustomErrorMessageKeys(List<String> customErrorMessageKeys);
	void setCustomErrorMessageValues(List<String> customErrorMessageValues);
	String getCustomMessage(String key);
	
	final static int ERROR_DISPLAY_TYPE_POPUP = 0x1;
	final static int ERROR_DISPLAY_TYPE_LABEL = 0x2;
	final static int ERROR_DISPLAY_TYPE_STYLE = 0x4;
	int getValidationErrorDisplayType();
	void setValidationErrorDisplayType(int validationErrorDisplayType);
	
	void setNormalStyle(String style);
	void setErrorStyle(String errorStyle);
}
