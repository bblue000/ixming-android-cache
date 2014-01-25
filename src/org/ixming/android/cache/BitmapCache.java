package org.ixming.android.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

public class BitmapCache {
	
	private static final IBitmapDownloader mDefBitmapDownloader = null;
	
	// 应用中使用的Bitmap配置
	private static final Config DEF_CONFIG = Config.RGB_565;
	// 加载图片时，提供的默认条件
	private static final Options newDefaultLoadOptions() {
		Options op = new Options();
		op.inPreferredConfig = DEF_CONFIG;
		op.inPurgeable = true;
		op.inInputShareable = true;
		return op;
	}
	// 转换url为物理存储时相应的文件名
	private static final String transferFileName(String url) {
		return url;
	}
	
//	public Bitmap getBitmap(Context context, String url) {
//		String fileName = transferFileName(url);
//		String filePath = "" + fileName;
//	}
}
