package com.ashera.widget;

public abstract class BaseAttributeCommand implements AttributeCommand {
	protected int priority;
	protected String phase;
	protected String id;

	public BaseAttributeCommand(String id) {
		super();
		this.id = id;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public boolean executeAfterPostMeasure() {
		return false;
	}

	@Override
	public void updatePhaseArgs(Object... args) {
	}
	
	@Override
	public Object getValue(String attributeName) {
		return null;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
