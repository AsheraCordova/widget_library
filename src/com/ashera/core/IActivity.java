package com.ashera.core;

import java.util.Map;

public interface IActivity {
    void onDeviceReady();
    IFragment getActiveRootFragment();
    void sendEventMessage(Map<String, Object> dataMap);
    String getAssetMode();
    String getDevServerIp();
	void storeUserData(String varName, Object objValue);
	Object getUserData(String varName);
	void storeInTempCache(String varName, Object objValue);
	Object getFromTempCache(String varName);
	Object getRootWidget();
	float getScaleFactor();
	String getPreference(String name);

}