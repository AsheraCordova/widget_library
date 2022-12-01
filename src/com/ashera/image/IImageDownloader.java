package com.ashera.image;

public interface IImageDownloader {
	void download(Object context, String url, Object placeholder, Object error, ITarget target);
}
