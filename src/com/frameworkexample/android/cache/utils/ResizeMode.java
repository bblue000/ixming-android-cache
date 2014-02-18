package com.frameworkexample.android.cache.utils;

/**
 * 缩放/转变Bitmap的模式。
 * <p/>
 * 应用中，通常需要指定缩放比率或者大小获取原Bitmap的一个副本，
 * <p/>
 * 而这些指定的大小，又有不同的理解方式：
 * <p/>
 * 1、只要尺寸与指定的不一致，就需要转换；<br/>
 * 2、尺寸大于指定大小，才需要转换；<br/>
 * 3、尺寸小于指定大小，才需要转换。<br/>
 * 
 * @author Yin Yong
 * @version 1.0
 */
public enum ResizeMode {

	/**
	 * 如果跟指定的大小不等，就需要缩放
	 */
	AlwaysIfDiffer {
		@Override
		boolean needResize(long rawMemSize, long targetMemSize) {
			return (rawMemSize != targetMemSize);
		}
		
		@Override
		boolean needResize(float scale) {
			return (scale != 1.0F);
		}
		
		@Override
		boolean needResize(int rawWidth, int rawHeight, int targetWidth,
				int targetHeight) {
			return (targetWidth != rawWidth || targetHeight != rawHeight);
		}
	},
	
	/**
	 * 如果比指定的大小大，就需要缩放至指定大小
	 */
	OnlyIfLarger {
		@Override
		boolean needResize(long rawMemSize, long targetMemSize) {
			return (rawMemSize > targetMemSize);
		}
		
		@Override
		boolean needResize(float scale) {
			// ignore scale factor
			return true;
		}
		
		@Override
		boolean needResize(int rawWidth, int rawHeight, int targetWidth,
				int targetHeight) {
			return (targetWidth > rawWidth && targetHeight > rawHeight);
		}
	},
	
	/**
	 * 如果比指定的大小小，就需要缩放至指定大小
	 */
	OnlyIfSmaller {
		@Override
		boolean needResize(long rawMemSize, long targetMemSize) {
			return (rawMemSize < targetMemSize);
		}
		
		@Override
		boolean needResize(float scale) {
			// ignore scale factor
			return true;
		}
		
		@Override
		boolean needResize(int rawWidth, int rawHeight, int targetWidth,
				int targetHeight) {
			return (targetWidth < rawWidth && targetHeight < rawHeight);
		}
	};
	
	/*package*/abstract boolean needResize(long rawMemSize, long targetMemSize);
	/*package*/abstract boolean needResize(float scale);
	/*package*/abstract boolean needResize(int rawWidth, int rawHeight, int targetWidth, int targetHeight);
}
