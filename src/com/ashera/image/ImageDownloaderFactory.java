package com.ashera.image;

public class ImageDownloaderFactory {
	private static IImageDownloader imageDownloader;

	public static IImageDownloader get() {
		return imageDownloader;
	}
	
	public static void register(IImageDownloader imageDownloader) {
		ImageDownloaderFactory.imageDownloader = imageDownloader;
	}
}
