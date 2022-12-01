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
