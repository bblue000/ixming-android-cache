package org.ixming.android.cache;

import java.io.FileDescriptor;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

public class PowerfulBitmapScaler {
public static Bitmap fromResourceWithMemSize(Context context, int resId, long size) {
		
	}
	
	public static Bitmap fromResourceWithScale(Context context, int resId, float scale) {
		if (scale <= 0F) {
			// incorrect values
			return null;
		}
		AssetFileDescriptor assetFileDescriptor = context.getResources().openRawResourceFd(resId);
		try {
			FileDescriptor fd = assetFileDescriptor.getFileDescriptor();
			Options justDecodeBounds = BitmapConstants.justDecodeBoundsOptions();
			BitmapUtils.fromFileDescriptor(fd, justDecodeBounds);
			int rawWidth = justDecodeBounds.outWidth;
			int rawHeight = justDecodeBounds.outHeight;
			int targetWith = (int) (rawWidth * scale);
			int targetHeight = (int) (rawHeight * scale);
			
			Options decodeBounds = BitmapConstants.defaultBitmapOptions();
			configDecodeBounds(rawWidth, rawHeight, scale, decodeBounds);
			return fromFileDescriptorWithSize(fd, targetWith, targetHeight, decodeBounds);
		} finally {
			try {
				assetFileDescriptor.close();
			} catch (Throwable e) { }
		}
	}
	
	public static Bitmap fromResourceWithSize(Context context, int resId, int width, int height) {
		if (width <= 0 || height <= 0) {
			// incorrect values
			return null;
		}
		AssetFileDescriptor assetFileDescriptor = context.getResources().openRawResourceFd(resId);
		try {
			FileDescriptor fd = assetFileDescriptor.getFileDescriptor();
			
			Options justDecodeBounds = BitmapConstants.justDecodeBoundsOptions();
			BitmapUtils.fromFileDescriptor(fd, justDecodeBounds);
			int rawWith = justDecodeBounds.outWidth;
			int rawHeight = justDecodeBounds.outHeight;
			int targetWith = (int) (rawWith * scale);
			int targetHeight = (int) (rawHeight * scale);
			
			Options decodeBounds = BitmapConstants.defaultBitmapOptions();
			float scaleReciprocal = 1F / scale;
			int sampleSize = (int) scaleReciprocal;
			if (sampleSize >= 1) {
				decodeBounds.inSampleSize = sampleSize;
			}
			return BitmapUtils.fromFileDescriptor(fd, decodeBounds);
		} finally {
			try {
				assetFileDescriptor.close();
			} catch (Throwable e) { }
		}
	}
	
	private static void configDecodeBounds(float scale, Options decodeBounds) {
		float scaleReciprocal = 1F / scale;
		int sampleSize = (int) scaleReciprocal;
		if (sampleSize >= 1) {
			decodeBounds.inSampleSize = sampleSize;
		}
	}
	
	private static Bitmap fromFileDescriptorWithSize(FileDescriptor fd,
			int width, int height, Options decodeBounds) {
		Bitmap bm = BitmapUtils.fromFileDescriptor(fd, decodeBounds);
		return BitmapUtils.scaleBitmapToSize(bm, width, height, true);
	}
}
