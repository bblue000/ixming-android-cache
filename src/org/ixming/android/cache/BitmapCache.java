package org.ixming.android.cache;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

import org.ixming.android.cache.utils.BitmapUtils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 负责存储和获取缓存
 * 
 * @author Yin Yong
 */
public class BitmapCache {
	
	private static final String TAG = BitmapCache.class.getSimpleName();
	
	private LruCache<String, Bitmap> mImageCache = null;
	
	private static BitmapCache mInstance = new BitmapCache();
	public static BitmapCache getInstance() {
		return mInstance;
	}
	
	public static void initialize() {
		// get
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int cacheSize = maxMemory / 8;
		mInstance.mImageCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				long bitSize = BitmapUtils.calculateBitmapMemSize(value);
				long rSize = bitSize / 1024;
				return (int) rSize;
			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				mInstance.mImageCache.remove(key);
				new PhantomReference<Bitmap>(oldValue,
						new ReferenceQueue<Bitmap>());
				oldValue = null;
				System.gc();
				LogUtils.i(TAG, "entry Removed old -> " + key);
			}
		};
	}
	
	public Bitmap getBitmap(String key) {
		synchronized (mImageCache) {
			return mImageCache.get(key);
		}
	}
	
	public void addToCache(String key, Bitmap bm) {
		synchronized (mImageCache) {
			mImageCache.put(key, bm);
		}
	}
}
