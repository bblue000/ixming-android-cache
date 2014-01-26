package org.ixming.android.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

public class BitmapCache {
	
	private static final IBitmapDownloader mDefBitmapDownloader = null;
	
	// 转换url为物理存储时相应的文件名
	private static final String transferFileName(String url) {
		return url;
	}
	
//	public Bitmap getBitmap(Context context, String url) {
//		String fileName = transferFileName(url);
//		String filePath = "" + fileName;
//	}
}
