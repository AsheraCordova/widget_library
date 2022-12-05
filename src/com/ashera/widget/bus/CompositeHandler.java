package com.ashera.widget.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CompositeHandler extends EventBusHandler {

	@com.google.j2objc.annotations.WeakOuter
	private class AccumulatingListener implements OnCompletedListener {

		private int _size;
		private int _accumulated;

		private AccumulatingListener() {
			this._size = _handlers.size();
			this._accumulated = 0;
		}
		
		@Override
		public void onCompleted() {
			++_accumulated;
			
			if (_accumulated >= _size)
				completed(_listener);
		}

	}

	private OnCompletedListener _listener;
	
	private List<EventBusHandler> _handlers = new ArrayList<EventBusHandler>();

	private OnCompletedListener _accumulator;

	public CompositeHandler(String type, EventBusHandler ... handlers) {
	    super(type);
		add(handlers);
	}

	public void add(EventBusHandler ... handler) {
		_handlers.addAll(Arrays.asList(handler));
	}
	
	public void clear(EventBusHandler handler) {
		_handlers.clear();
	}
	
	@Override
	public void perform(Object payload, OnCompletedListener listener) {
		this._listener = listener;
		this._accumulator = new AccumulatingListener();
		doPerform(payload);
	}
	
	@Override
	protected void doPerform(Object payload) {
		// copy it into a temp list
		ArrayList<EventBusHandler> handlers = new ArrayList<>(_handlers);
		for (EventBusHandler handler : handlers)
			handler.perform(payload, this._accumulator);
	}

    public void remove(EventBusHandler eventBusHandler) {
        _handlers.remove(eventBusHandler);
        
    }

	
	public List<EventBusHandler> getHandlers() {
		return _handlers;
	}


}
