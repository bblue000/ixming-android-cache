package com.frameworkexample.android.cache;

import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

public abstract class BitmapConstants {
	BitmapConstants() { }
	
	public static final int MAX_VIEW_STRATEGY_POOL_SIZE = 5;
	public static final int MAX_IMAGEVIEW_STRATEGY_POOL_SIZE = 10;
	public static final int MAX_BITMAP_TRASITION_POOL_SIZE = 20;
	
	/**
	 * 应用中使用的Bitmap配置
	 */
	public static final Config DEF_CONFIG = Config.RGB_565;
	
	/**
	 * 加载图片时，提供的默认条件
	 */
	public static final Options defaultBitmapOptions() {
		Options op = new Options();
		op.inPreferredConfig = DEF_CONFIG;
		op.inPurgeable = true;
		op.inInputShareable = true;
		return op;
	}
	
	/**
	 * 加载图片时，只图片的加载宽高
	 */
	public static final Options justDecodeBoundsOptions() {
		Options op = new Options();
		op.inJustDecodeBounds = true;
		return op;
	}
	
	/**
	 * 应用中加载图片的默认设置代表的单个像素的字节数
	 */
	public static final int defaultConfigBytesPerPixel() {
		switch (DEF_CONFIG) {
		case ARGB_8888:
			return 4;
		case RGB_565:
		case ARGB_4444:
			return 2;
		case ALPHA_8:
		default:
			return 1;
		}
	}
}
