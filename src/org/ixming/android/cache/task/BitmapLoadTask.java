package org.ixming.android.cache.task;

import java.lang.ref.WeakReference;

import org.ixming.android.cache.BitmapConstants;
import org.ixming.android.cache.utils.BitmapTransition;
import org.ixming.android.cache.utils.PowerfulBitmapResizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;

public class BitmapLoadTask extends AsyncTask<Object, Integer, Integer> {
	// 转换url为物理存储时相应的文件名
	private static final String transferFileName(String url) {
		return url;
	}
	private static final int RESULT_UNDEFINED = 0;
	private static final int RESULT_SUCCESS = 1;
	private static final int RESULT_FAILED = 2;
	
	private WeakReference<View> mRef;
	private BitmapDisplayConfig mBitmapDisplayConfig;
	public BitmapLoadTask() {
		
	}
	
	private void reset() {
		mRef = null;
		mBitmapDisplayConfig = null;
	}
	
	private View checkAndGetView() {
		return mRef.get();
	}
	
	@Override
	protected Integer doInBackground(Object... params) {
//		if (!checkViewExists()) {
//			cancel(true);
//		}
		return null;
	}
	
	@Override
	protected void onCancelled(Integer result) {
		
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		View targetView;
		//TODO 如果此时目标View已经被回收了，则直接返回
		try {
			targetView = checkAndGetView();
		} catch (Exception e) {
			return ;
		}
		int resultInt = 0;
		if (null != result) {
			resultInt = result.intValue();
		}
		
		switch (resultInt) {
			case RESULT_SUCCESS: {
				BitmapTransition transition = mBitmapDisplayConfig.getTransition();
				Bitmap resultBitmap = null;
				if (null != transition) {
					resultBitmap = PowerfulBitmapResizer.fromFile("",
							transition,
							BitmapConstants.defaultBitmapOptions());
				}
				if (null != resultBitmap) {
					mBitmapDisplayConfig.getLoadedDisplayStrategy().display(
							targetView, new BitmapDrawable(null, resultBitmap));
				}
				break;
			}
			case RESULT_FAILED: {
				Drawable failDrawable = mBitmapDisplayConfig.getFailedDrawable();
				if (null != failDrawable) {
					mBitmapDisplayConfig.getFailedDisplayStrategy().display(targetView, failDrawable);
				}
				break;
			}
			case RESULT_UNDEFINED:
			default: {
				break;
			}
		}
	}
	
	
//	public <V extends View>void getAndSetBitmap(Context context,
//			V targetView, String url) {
//		Bitmap resultBm = null;
//		String fileName = transferFileName(url);
//		String filePath = "" + fileName;
//		
//		// 1、尝试从缓存中加载
//		resultBm = getFromCache(filePath);
//		if (null != resultBm) {
//			// loaded from cache
//			displayer.getLoadedStrategy().display(targetView,
//					new BitmapDrawable(context.getResources(), resultBm));
//			return ;
//		}
//		//TODO 显示默认的图片
//		displayer.getDecorStrategy().display(targetView, null);
//		
//		// 2、尝试从文件加载
//		resultBm = getFromFile(filePath);
//		if (null != resultBm) {
//			// loaded from file
//			// add to cache and display
//			addToCache(filePath, resultBm);
//			displayer.getLoadedStrategy().display(targetView, null);
//			return ;
//		}
//		
//		// 3、尝试从网络加载
//		resultBm = getFromNetwork(url);
//		if (null != resultBm) {
//			// loaded from file
//			// add to cache and display
//			addToCache(filePath, resultBm);
//			displayer.getLoadedStrategy().display(targetView, null);
//		}
//	}
}
