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
package com.ashera.utils;

import java.util.List;

import com.ashera.core.IActivity;
import com.ashera.widget.IWidget;
import com.ashera.widget.PluginInvoker;

public class CommandHelper {
	public static interface SucessCallBack {
		void success(Object command, String payload);
	}
	
	public static void executeCommand(IActivity activity, Object command, final String strpayLoad, final com.ashera.utils.CommandHelper.SucessCallBack successCallBack) {
		final com.ashera.core.IFragment fragment = activity.getActiveRootFragment();
		IWidget w = fragment.getRootWidget();
        Object payLoad = PluginInvoker.unmarshal(strpayLoad, List.class);
        if (payLoad != null) {
			 List<Object> commandDataArr = PluginInvoker.getList(payLoad);
			 try {
				 fragment.disableRemeasure();
	             w.executeCommand(w, commandDataArr, null, com.ashera.widget.IWidget.COMMAND_EXEC_SETTER_METHOD);
			 } finally {
				 fragment.enableRemeasure();
			 }
             fragment.remeasure();
             w.executeCommand(w, commandDataArr, null, com.ashera.widget.IWidget.COMMAND_EXEC_GETTER_METHOD);
        }
        
        successCallBack.success(command, PluginInvoker.marshal(payLoad));
	}
}
