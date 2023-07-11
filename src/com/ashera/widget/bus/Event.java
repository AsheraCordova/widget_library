package com.ashera.widget.bus;

public class Event {
	public enum StandardEvents {
		orientationChange, customEvent, initialise, orientationChangePostParentMeasure, postMeasure, preMeasure, dealloc, outsideClicked;
	}
	private StandardEvents type;
	private Object additionalData;
	
	public Event(StandardEvents type, Object additionalData) {
		super();
		this.type = type;
		this.additionalData = additionalData;
	}

	public Event(StandardEvents type) {
		super();
		this.type = type;
	}

	public StandardEvents getType() {
		return type;
	}

	public void setType(StandardEvents type) {
		this.type = type;
	}

	public Object getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Object additionalData) {
		this.additionalData = additionalData;
	}
	
}
