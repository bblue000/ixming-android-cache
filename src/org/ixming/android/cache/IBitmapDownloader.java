package org.ixming.android.cache;

public interface IBitmapDownloader {

	/**
	 * 根据提供的URL下载图片
	 * @param url 提供下载的路径
	 * @return 下载成功则返回true，否则返回false
	 */
	public boolean download(String url);
	
}
