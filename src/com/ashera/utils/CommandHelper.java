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
             w.executeCommand(w, commandDataArr, null, com.ashera.widget.IWidget.COMMAND_EXEC_SETTER_METHOD);
             fragment.remeasure();
             w.executeCommand(w, commandDataArr, null, com.ashera.widget.IWidget.COMMAND_EXEC_GETTER_METHOD);
        }
        
        successCallBack.success(command, PluginInvoker.marshal(payLoad));
	}
}
