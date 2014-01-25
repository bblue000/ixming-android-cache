package org.ixming.android.cache;

import android.graphics.Bitmap;

public class SimpleBitmapScaler {

	private static final String TAG = SimpleBitmapScaler.class.getSimpleName();
	
	private SimpleBitmapScaler() { }
	
	static BitmapTransition checkBitmapTransitionValue(BitmapTransition transition) {
		if (null == transition) {
			return BitmapTransition.obtain();
		}
		return transition;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//TODO 如果已经存在了原Bitmap
	/**
	 * 缩放src到指定大小（内存大小）
	 * @param src 原Bitmap
	 * @param size 指定大小
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的Bitmap
	 * 
	 * @see BitmapTransition
	 */
	public static Bitmap scaleBitmapToMemSize(Bitmap src, long size, BitmapTransition transition) {
		return scaleBitmapToMemSize0(src, size, transition, true);
	}
	
	/**
	 * 缩放src到指定大小（缩放比率）
	 * @param src 原Bitmap
	 * @param scale 指定宽高缩放比率
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的Bitmap
	 * 
	 * @see BitmapTransition
	 */
	public static Bitmap scaleBitmapToSize(Bitmap src, float scale, BitmapTransition transition) {
		return scaleBitmapToSize0(src, scale, transition, true);
	}
	
	/**
	 * 缩放src到指定大小（宽高）
	 * @param src 原Bitmap
	 * @param width 指定宽
	 * @param height 指定高
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的Bitmap
	 * 
	 * @see BitmapTransition
	 */
	public static Bitmap scaleBitmapToSize(Bitmap src, int width, int height, BitmapTransition transition) {
		return scaleBitmapToSize0(src, width, height, transition, true);
	}
	
	/**
	 * 缩放src到指定大小（内存）， 并转为byte数组
	 * @param src 原Bitmap
	 * @param size 指定大小
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的byte数组
	 * 
	 * @see BitmapTransition
	 */
	public static byte[] scaleBitmapIfNeededToMemSize(Bitmap src, long size, BitmapTransition transition) {
		try {
			Bitmap resizedSrc = scaleBitmapToMemSize(src, size, transition);
			return BitmapUtils.bmpToByteArray(resizedSrc, (resizedSrc != src));
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToSize<size> Exception: " + e.getMessage());
			return (byte[]) null;
		}
	}
	
	/**
	 * 缩放src到指定大小（宽高）， 并转为byte数组
	 * @param src 原Bitmap
	 * @param width 指定宽
	 * @param height 指定高
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的byte数组
	 * 
	 * @see BitmapTransition
	 */
	public static byte[] scaleBitmapIfNeededToSize(Bitmap src, int width, int height, BitmapTransition transition) {
		Bitmap resizedSrc = scaleBitmapToSize(src, width, height, transition);
		return BitmapUtils.bmpToByteArray(resizedSrc, (resizedSrc != src));
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//TODO inner implements
	private static Bitmap scaleBitmapToMemSize0(Bitmap src, long size, BitmapTransition transition, boolean needCheckMode) {
		try {
			transition = checkBitmapTransitionValue(transition);
			float bmSize = BitmapUtils.calculateBitmapMemSize(src);
			if (needCheckMode) {
				switch (transition.getScaleMode()) {
				case AlwaysIfDiffer:
					if (bmSize == size) {
						return src;
					}
					break;
				case OnlyIfLarger:
					if (bmSize <= size) {
						return src;
					}
					break;
				case OnlyIfSmaller:
					if (bmSize >= size) {
						return src;
					}
					break;
				}
			}
			float scale = (float) Math.sqrt(size / bmSize);
			return scaleBitmapToSize0(src, scale, transition, false);
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToMemSize0 Exception: " + e.getMessage());
			return src;
		}
	}
	
	private static Bitmap scaleBitmapToSize0(Bitmap src, float scale, BitmapTransition transition, boolean needCheckMode) {
		try {
			if (scale <= 0F) {
				// unexpected values
				return src;
			}
			transition = checkBitmapTransitionValue(transition);
			if (needCheckMode) {
				switch (transition.getScaleMode()) {
				case AlwaysIfDiffer:
					if (scale == 1.0F) {
						return src;
					}
					break;
				case OnlyIfLarger:
					if (scale <= 1.0F) {
						return src;
					}
					break;
				case OnlyIfSmaller:
					if (scale >= 1.0F) {
						return src;
					}
					break;
				}
			}
			float width = src.getWidth();
			float height = src.getHeight();
			int scaledWidth = (int) (width * scale + 0.5F);
			int scaledHeight = (int) (height * scale + 0.5F);
			return scaleBitmapToSize0(src, scaledWidth, scaledHeight, transition, false);
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToSize0<scale> Exception: " + e.getMessage());
			return src;
		}
	}
	
	private static Bitmap scaleBitmapToSize0(Bitmap src, int width, int height, BitmapTransition transition, boolean needCheckMode) {
		try {
			if (width <= 0 || height <= 0) {
				// unexpected values
				return src;
			}
			transition = checkBitmapTransitionValue(transition);
			if (needCheckMode) {
				int rawWidth = src.getWidth();
				int rawHeight = src.getHeight();
				switch (transition.getScaleMode()) {
				case AlwaysIfDiffer:
					if (width == rawWidth && height == rawHeight) {
						return src;
					}
					break;
				case OnlyIfLarger:
					if (width <= rawWidth && height <= rawHeight) {
						return src;
					}
					break;
				case OnlyIfSmaller:
					if (width >= rawWidth && height >= rawHeight) {
						return src;
					}
					break;
				}
			}
			Bitmap target = Bitmap.createScaledBitmap(src, width, height, true);
			// 当宽高和原先一致时，会直接返回当前src，此时，不应当recycle
			if (src != target && transition.getRecycleSrc()) {
				src.recycle();
			}
			return target;
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToSize0<w, h> Exception: " + e.getMessage());
			return src;
		}
	}
}
