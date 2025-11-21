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
package com.ashera.widget.bus;

public abstract class EventBusHandler {
    private String type;

    public EventBusHandler(String type) {
        this.type = type;
    }
	public interface OnCompletedListener {
		public void onCompleted();
	}

	
	/**
	 * Executes this handler with a given payload
	 * 
	 * @param payload
	 */
	public void perform(Object payload) {
		perform(payload, null);
	}
	
	/**
	 * Executes this handler with a given payload and 
	 * a listener that will be notified when this operation is complete
	 * 
	 * @param payload
	 * @param listener
	 */
	public void perform(Object payload, OnCompletedListener listener) {
		doPerform(payload);
		completed(listener);
	}
	
	protected void completed(OnCompletedListener listener) {
		if (listener != null)
			listener.onCompleted();
	}

	protected abstract void doPerform(Object payload);
	
    public String getType() {
        return type;
    }	
}
