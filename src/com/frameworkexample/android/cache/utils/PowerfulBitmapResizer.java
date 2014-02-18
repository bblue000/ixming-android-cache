package com.frameworkexample.android.cache.utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.frameworkexample.android.cache.BitmapConstants;
import com.frameworkexample.android.utils.LogUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * 不同于 {@link SimpleBitmapResizer} 在拥有原Bitmap的情况下对之创建新的副本（需要缩放的情况下）；
 * <p/>
 * 本类将着眼于加载Bitmap之前的分析判断，根据需要的大小“恰当”地调整一些策略，使得加载速率有所提升，
 * 内存使用有所优化。
 * 
 * @author Yin Yong
 */
public class PowerfulBitmapResizer {
	
	private static final String TAG = PowerfulBitmapResizer.class.getSimpleName();
	
	private static Options checkOptionsValue(Options op) {
		if (null == op) {
			return BitmapConstants.defaultBitmapOptions();
		}
		return op;
	}
	
	public static Bitmap fromResource(Context context, int resId,
			BitmapTransition transition, Options decodeBounds) {
		transition = BitmapTransition.checkValue(transition);
		try {
			Options justDecodeBoundsOp = BitmapConstants.justDecodeBoundsOptions();
			BitmapUtils.fromResource(context, resId, justDecodeBoundsOp);
			
			decodeBounds = checkOptionsValue(decodeBounds);
			checkResizableAndCalSampleSize(justDecodeBoundsOp, transition, decodeBounds);
			
			LogUtils.d(TAG, "fromResource inSampleSize: " + decodeBounds.inSampleSize);
			
			Bitmap src = BitmapUtils.fromResource(context, resId, decodeBounds);
			return SimpleBitmapResizer.resizeBitmap(src, transition.setRecycleSrc(true));
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromResource Exception: " + e.getMessage());
			return null;
		} finally {
			transition.recycle();
		}
	}
	
	/**
	 * 从assets中加载图片
	 * @return null if errors occur
	 */
	public static Bitmap fromAssets(Context context, String fileName,
			BitmapTransition transition, BitmapFactory.Options decodeBounds) {
		transition = BitmapTransition.checkValue(transition);
		try {
			Options justDecodeBoundsOp = BitmapConstants.justDecodeBoundsOptions();
			BitmapUtils.fromAssets(context, fileName, justDecodeBoundsOp);
			
			decodeBounds = checkOptionsValue(decodeBounds);
			checkResizableAndCalSampleSize(justDecodeBoundsOp, transition, decodeBounds);
			
			LogUtils.d(TAG, "fromAssets inSampleSize: " + decodeBounds.inSampleSize);
			
			Bitmap src = BitmapUtils.fromAssets(context, fileName, justDecodeBoundsOp);
			return SimpleBitmapResizer.resizeBitmap(src, transition.setRecycleSrc(true));
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromAssets Exception: " + e.getMessage());
			return null;
		} finally {
			transition.recycle();
		}
	}
	
	public static Bitmap fromFile(String fileName, BitmapTransition transition,
			BitmapFactory.Options decodeBounds) {
		transition = BitmapTransition.checkValue(transition);
		try {
			FileInputStream fin = new FileInputStream(fileName);
			try {
				return fromFileDescriptor(fin.getFD(), transition, decodeBounds);
			} finally {
				BitmapUtils.checkClose(fin, true);
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFile Exception: " + e.getMessage());
			return null;
		} finally {
			transition.recycle();
		}
	}
	
	public static Bitmap fromFile(File file, BitmapTransition transition,
			BitmapFactory.Options decodeBounds) {
		return fromFile(file.getAbsolutePath(), transition, decodeBounds);
	}
	
	public static Bitmap fromFileOutputStream(FileOutputStream out,
			BitmapTransition transition, BitmapFactory.Options decodeBounds, boolean closeStream) {
		try {
			return fromFileDescriptor(out.getFD(), transition, decodeBounds);
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFileOutputStream Exception: " + e.getMessage());
			return null;
		} finally {
			BitmapUtils.checkClose(out, closeStream);
		}
	}
	
	public static Bitmap fromFileDescriptor(FileDescriptor fd,
			BitmapTransition transition, Options decodeBounds) {
		transition = BitmapTransition.checkValue(transition);
		try {
			Options justDecodeBoundsOp = BitmapConstants.justDecodeBoundsOptions();
			BitmapUtils.fromFileDescriptor(fd, justDecodeBoundsOp);
			
			decodeBounds = checkOptionsValue(decodeBounds);
			checkResizableAndCalSampleSize(justDecodeBoundsOp, transition, decodeBounds);
			LogUtils.d(TAG, "fromFileDescriptor inSampleSize: " + decodeBounds.inSampleSize);
			Bitmap src = BitmapUtils.fromFileDescriptor(fd, decodeBounds);
			return SimpleBitmapResizer.resizeBitmap(src, transition.setRecycleSrc(true));
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFileDescriptor Exception: " + e.getMessage());
			return null;
		} finally {
			transition.recycle();
		}
	}
	
	private static boolean checkResizableAndCalSampleSize(Options justDecodeBoundsOp,
			BitmapTransition transition, Options decodeBounds) {
		int rawWidth = justDecodeBoundsOp.outWidth;
		int rawHeight = justDecodeBoundsOp.outHeight;
		if (transition.checkResizableWithSrc(rawWidth, rawHeight,
				rawWidth * rawHeight * BitmapUtils.byteCountOfPerPixel(decodeBounds.inPreferredConfig))) {
			int targetWidth = transition.getTargetWidth();
			int targetHeight = transition.getTargetHeight();
			
			float scaleFactorByWidth = ((float) targetWidth) / ((float) rawWidth);
			float scaleFactorByHeight = ((float) targetHeight) / ((float) rawHeight);
			float scaleFactor = Math.min(scaleFactorByWidth, scaleFactorByHeight);
			
			if (decodeBounds.inSampleSize <= 1) {
				//TODO if user does not set
				int inSampleSize = 1;
				if (scaleFactor < 1.0F) {
					//TODO offset value，如果xx.9，则认为能够晋升为xx + 1
					double OFFSET = 0.05;
					//TODO 如果大小比率小于1.0，则可能需要根据scaleFactor判断，能不能在加载时就执行缩放
					inSampleSize = Math.max(1, (int) (1D / scaleFactor + OFFSET));
				}
				decodeBounds.inSampleSize = inSampleSize;
			}
			return true;
		}
		return false;
	}
	
}
