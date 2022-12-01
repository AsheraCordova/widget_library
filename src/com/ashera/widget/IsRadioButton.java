package com.ashera.widget;

public interface IsRadioButton {
	void setChecked(boolean checked);
	boolean isChecked();
	void addCheckedListener(Object listener, String id);
}
