package com.ashera.widget;


public interface ICompositeDecorator extends HasWidgets {
	public ICompositeDecorator newInstance(String localName, IWidget wrappedWidget);
	public void addDecorator(IDecorator decorator);
	public IWidget getWrappedWidget();
	void setAttribute(WidgetAttribute key,
			String strValue, Object objValue,
			ILifeCycleDecorator lifeCycleDecorator, boolean parentView);
	void add(IWidget w, boolean parentView);
	boolean supportsAttribute(String attributeName);
}
