package org.ixming.android.cache;

import android.graphics.Bitmap;

public class SimpleBitmapResizer {

	private static final String TAG = SimpleBitmapResizer.class.getSimpleName();
	
	private SimpleBitmapResizer() { /* it is a utility class */ }
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//TODO 如果已经存在了原Bitmap
	/**
	 * 根据指定的 {@link BitmapTransition} ，改变原Bitmap的大小
	 * @param src 原Bitmap
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的Bitmap
	 * 
	 * @see BitmapTransition
	 */
	public static Bitmap resizeBitmap(Bitmap src, BitmapTransition transition) {
		transition = BitmapTransition.checkValue(transition);
		try {
			if (transition.checkResizableWithSrc(src)) {
				try {
					Bitmap target = Bitmap.createScaledBitmap(src,
							transition.getTargetWidth(),
							transition.getTargetHeight(), true);
					//TODO 当宽高和原先一致时，会直接返回当前src，此时，不应当recycle
					if (src != target && transition.getRecycleSrc()) {
						src.recycle();
					}
					return target;
				} catch (Throwable e) {
					LogUtils.e(TAG, "resizeBitmap Exception: " + e.getMessage());
				}
			}
			return src;
		} finally {
			transition.recycle();
		}
	}
	
	/**
	 * 缩放src到指定大小（内存）， 并转为byte数组
	 * @param src 原Bitmap
	 * @param transition if null, 所有的属性使用默认值
	 * @return 处理后的byte数组
	 * 
	 * @see BitmapTransition
	 */
	public static byte[] toByteArrayAndResize(Bitmap src, BitmapTransition transition) {
		transition = BitmapTransition.checkValue(transition);
		Bitmap resizedSrc = resizeBitmap(src, transition);
		return BitmapUtils.bmpToByteArray(resizedSrc, (transition.getRecycleSrc() || resizedSrc != src));
	}
	
}
