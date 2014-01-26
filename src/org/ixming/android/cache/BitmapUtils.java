package org.ixming.android.cache;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

/**
 * 工具类，部分功能仅仅是包装了 {@link BitmapFactory} 中的方法，
 * 
 * 不同的是，此类中提供的方法不会抛出异常，而是当发生错误时返回null，所以需要检测是否为空。
 * 
 * @author Yin Yong
 */
public class BitmapUtils {

	private static final String TAG = BitmapUtils.class.getSimpleName();
	private BitmapUtils() { }
	
	/**
	 * 从res中加载图片
	 * @return null if errors occur
	 */
	public static Bitmap fromResource(Context context, int resId, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
		} catch (Exception e) {
			LogUtils.e(TAG, "fromResource Exception: " + e.getMessage());
		}
		return bitmap;
	}
	
	/**
	 * 从assets中加载图片
	 * @return null if errors occur
	 */
	public static Bitmap fromAssets(Context context, String fileName,
			BitmapFactory.Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = fromInputStream(context.getAssets().open(fileName), options, true);
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromAssets Exception: " + e.getMessage());
		}
		return bitmap;
	}
	
	public static Bitmap fromFile(String fileName, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(fileName, options);
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFile<String, Options> Exception: " + e.getMessage());
		}
		return bitmap;
	}
	
	public static Bitmap fromFile(File file, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFile<File, Options> Exception: " + e.getMessage());
		}
		return bitmap;
	}
	
	/**
	 * 从输入流中加载图片
	 * @return null if errors occur
	 */
	public static Bitmap fromInputStream(InputStream in,
			BitmapFactory.Options options, boolean closeStream) {
		Bitmap bitmap = null;
		try {
			if (in instanceof FileInputStream) {
				FileInputStream fIn = (FileInputStream) in;
				bitmap = fromFileDescriptor(fIn.getFD(), options);
			} else {
				bitmap = BitmapFactory.decodeStream(in, null, options);
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromInputStream Exception: " + e.getMessage());
		} finally {
			checkClose(in, closeStream);
		}
		return bitmap;
	}
	
	/**
	 * 从指定的FileOutputStream中（获取其对应的FileDescriptor）加载Bitmap
	 */
	public static Bitmap fromFileOutputStream(FileOutputStream out,
			BitmapFactory.Options options, boolean closeStream) {
		Bitmap bitmap = null;
		try {
			bitmap = fromFileDescriptor(out.getFD(), options);
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFileOutputStream Exception: " + e.getMessage());
		} finally {
			checkClose(out, closeStream);
		}
		return bitmap;
	}
	
	/**
	 * 从指定的FileDescriptor中加载Bitmap
	 */
	public static Bitmap fromFileDescriptor(FileDescriptor fd,
			BitmapFactory.Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
		} catch (Throwable e) {
			LogUtils.e(TAG, "fromFileDescriptor Exception: " + e.getMessage());
		} 
		return bitmap;
	}
	
	/**
	 * 计算Bitmap的内存占用
	 * @param bm 要计算的对象
	 * @return 内存大小，以byte为单位
	 */
	public static long calculateBitmapMemSize(Bitmap bm) {
		if (null == bm) {
			return 0L;
		}
		return bm.getRowBytes() * bm.getHeight();
	}
	
	public static int byteCountOfPerPixel(Config config) {
		switch (config) {
		case ARGB_8888:
			return 4;
		case RGB_565:
		case ARGB_4444:
			return 2;
		case ALPHA_8:
			return 1;
		default:
			return BitmapConstants.defaultConfigBytesPerPixel();
		}
	}
	
	/**
	 * 将Bitmap转化为字节数组
	 * 
	 * @param bmp
	 *            target bitmap to read
	 * @param needRecycle
	 *            是否需要回收bitmap
	 * @added 1.0
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		if (null == bmp) {
			return null;
		}
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			bmp.compress(CompressFormat.PNG, 100, output);
			if (needRecycle) {
				bmp.recycle();
			}
			return output.toByteArray();
		} catch (Exception e) {
			LogUtils.e(TAG, "bmpToByteArray Exception: " + e.getMessage());
			return null;
		} finally {
			checkClose(output, true);
		}
	}
	
	/*package*/ static void checkClose(Closeable close, boolean closeStream) {
		try {
			if (closeStream) {
				close.close();
			}
		} catch (Throwable ignore) { }
	}
}
