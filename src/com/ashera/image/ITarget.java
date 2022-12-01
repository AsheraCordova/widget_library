package com.ashera.image;

public interface ITarget {
	void onBitmapLoaded(Object bitmap);
	void onBitmapFailed(final Object errorDrawable);
	void onPrepareLoad(final Object placeHolderDrawable);
}
