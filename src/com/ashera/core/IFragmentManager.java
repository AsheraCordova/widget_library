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
package com.ashera.core;

import java.util.Map;

/**
 * Fragment can be configured with list of managers which implement all the lifecycle methods. 
 */
public interface IFragmentManager {
	void onCreate(IFragment fragment, Object... args);
	void onSaveInstanceState(IFragment fragment, Object... args);
	void onPause(IFragment fragment);
	void onResume(IFragment fragment);
	void onStart(IFragment fragment);
	void onStop(IFragment fragment);
	void onAttach(IFragment fragment);
	void onDetach(IFragment fragment);
	void onDestroy(IFragment fragment);
	void onRequestPermissionsResult(IFragment fragment, int requestCode, String[] permissions, int[] grantResults);
	IFragmentManager newInstance();
	void sendEvent(String action, Map<String, String> extraData);
}
