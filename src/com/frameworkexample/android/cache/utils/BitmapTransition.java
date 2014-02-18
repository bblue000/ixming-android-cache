package com.frameworkexample.android.cache.utils;

import java.util.LinkedList;

import com.frameworkexample.android.cache.BitmapConstants;

import android.graphics.Bitmap;

/**
 * 如果使用该工具中的一些方法——如 {@link SimpleBitmapResizer} 、  {@link PowerfulBitmapResizer}等类中的方法，
 * 需要使用到这个类。
 * <p/>
 * 该类包含了一些参数设定，概念区分等。
 * <p/>
 * 参数包括：<br/>
 * <table border="1px black" style="width:100%">
 * 	<tr style="text-align:center; font-weight: bold; background-color: #CCC">
 * 		<td>参数</td><td>含义</td>
 * 	</tr>
 * 	<tr>
 * 		<td>{@link #setRecycleSrc(boolean)}</td><td>操作完成后，是否回收原Bitmap</td>
 * 	</tr>
 * 	<tr>
 * 		<td>{@link #setResizeMode(ResizeMode)}</td><td>设置调整大小的模式，见{@link ResizeMode}</td>
 * 	</tr>
 * 	<tr>
 * 		<td>{@link #setTargetSize(int, int)}</td><td>根据目标像素大小调整加载Bitmap的大小</td>
 * 	</tr>
 * 	<tr>
 * 		<td>{@link #setTargetMemSize(long)}</td><td>根据目标内存大小调整加载Bitmap的大小</td>
 * 	</tr>
 * 	<tr>
 * 		<td>{@link #setScaleFactor(float)}</td><td>根据指定的缩放比率调整加载Bitmap的大小</td>
 * 	</tr>
 * </table>
 * <br/>
 * <b>注意：</b>调整Bitmap大小的方式是互斥的，只能保留最新设置
 * <p/>
 * 通过 {@link #obtain()} 方法获取实例。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public class BitmapTransition {

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// restrict the number of instances
	private static final Object sPoolSync = new Object();
	private static final int MAX_POOL_SIZE = BitmapConstants.MAX_BITMAP_TRASITION_POOL_SIZE;
    private static LinkedList<BitmapTransition> sPool = new LinkedList<BitmapTransition>();
	
    static BitmapTransition checkValue(BitmapTransition transition) {
		if (null == transition) {
			return BitmapTransition.obtain();
		}
		return transition;
	}
    
	public static BitmapTransition obtain() {
		synchronized (sPoolSync) {
			if (!sPool.isEmpty()) {
				return sPool.remove().reset();
			}
			return new BitmapTransition();
		}
	}

	private ResizeMode mResizeMode;
	private boolean mRecycleSrc;
	
	private static final int UNDEFINED = -1;
	private long mTargetMemSize;
	private int mTargetWidth;
	private int mTargetHeight;
	private float mScaleFactor;
	private BitmapTransition() {
		reset();
	}
	
	private BitmapTransition reset() {
		mIsRecycled = false;
		
		mResizeMode = ResizeMode.AlwaysIfDiffer;
		mRecycleSrc = false;
		resetTargetValues();
		return this;
	}
	
	private BitmapTransition resetTargetValues() {
		mTargetMemSize = UNDEFINED;
		mTargetWidth = UNDEFINED;
		mTargetHeight = UNDEFINED;
		mScaleFactor = UNDEFINED;
		return this;
	}
	
	public void recycle() {
		synchronized (sPoolSync) {
			if (sPool.size() < MAX_POOL_SIZE) {
				this.recycleMeInternal();
				sPool.add(this);
			}
		}
	}
	
	private boolean mIsRecycled = false;
	private void recycleMeInternal() {
		mIsRecycled = true;
	}
	
	private void checkIsRecycledInternal() {
		if (mIsRecycled) {
			throw new UnsupportedOperationException("this instance is recycled, use obtain to get a new one!");
		}
	}
	
	/**
	 * 该方法用于指定转变Bitmap大小的模式。
	 * <p/>
	 * 默认值为 {@code ScaleMode#AlwaysIfDiffer}
	 * @see {@link ResizeMode}
	 */
	public BitmapTransition setResizeMode(ResizeMode mode) {
		checkIsRecycledInternal();
		if (null != mode) {
			mResizeMode = mode;
		}
		return this;
	}
	
	public ResizeMode getResizeMode() {
		checkIsRecycledInternal();
		return mResizeMode;
	}
	
	/**
	 * 该方法用于指定转变Bitmap后是否回收（recycle）原Bitmap。
	 */
	public BitmapTransition setRecycleSrc(boolean recycleSrc) {
		checkIsRecycledInternal();
		mRecycleSrc = recycleSrc;
		return this;
	}
	
	/**
	 * 默认为false
	 */
	public boolean isRecycleSrc() {
		checkIsRecycledInternal();
		return mRecycleSrc;
	}

	public BitmapTransition setTargetMemSize(long memSize) {
		checkIsRecycledInternal();
		if (memSize > 0) {
			resetTargetValues();
			mTargetMemSize = memSize;
		}
		return this;
	}
	
	public long getTargetMemSize() {
		checkIsRecycledInternal();
		return mTargetMemSize;
	}
	
	public BitmapTransition setScaleFactor(float scaleFactor) {
		checkIsRecycledInternal();
		if (scaleFactor > 0.0F) {
			resetTargetValues();
			mScaleFactor = scaleFactor;
		}
		return this;
	}
	
	public float getScaleFactor() {
		checkIsRecycledInternal();
		return mScaleFactor;
	}
	
	public BitmapTransition setTargetSize(int targetWidth, int targetHeight) {
		checkIsRecycledInternal();
		if (targetWidth > 0 && targetHeight > 0) {
			resetTargetValues();
			mTargetWidth = targetWidth;
			mTargetHeight = targetHeight;
		}
		return this;
	}
	
	public int getTargetWidth() {
		checkIsRecycledInternal();
		return mTargetWidth;
	}
	
	public int getTargetHeight() {
		checkIsRecycledInternal();
		return mTargetHeight;
	}
	
	
	/**
	 * 该方法由机制内部使用，外部不必要访问
	 * 
	 * @return 如果根据当前的BitmapTransition，以及原始Bitmap的rawWidth，rawHeight进行判断后，发现需要改变大小，则返回true
	 */
	/*package*/ boolean checkResizableWithSrc(Bitmap src) {
		int rawWidth = src.getWidth();
		int rawHeight = src.getHeight();
		return checkResizableWithSrc(rawWidth, rawHeight, BitmapUtils.calculateBitmapMemSize(src));
	}
	
	/**
	 * 该方法由机制内部使用，外部不必要访问
	 * 
	 * @return 如果根据当前的BitmapTransition，以及原始Bitmap的rawWidth，rawHeight进行判断后，发现需要改变大小，则返回true
	 */
	/*package*/ boolean checkResizableWithSrc(int rawWidth, int rawHeight, long srcMemSize) {
		checkIsRecycledInternal();
		boolean needResize = false;
		if (mTargetWidth != UNDEFINED && mTargetHeight != UNDEFINED) {
			if (getResizeMode().needResize(rawWidth, rawHeight, mTargetWidth, mTargetHeight)) {
				needResize = true;
			}
		}
		else if (mScaleFactor != UNDEFINED) {
			if (getResizeMode().needResize(mScaleFactor)) {
				int targetWidth = (int) (rawWidth * mScaleFactor + 0.5F);
				int targetHeight = (int) (rawHeight * mScaleFactor + 0.5F);
				setTargetSize(targetWidth, targetHeight);
				needResize = true;
			}
		}
		else if (mTargetMemSize != UNDEFINED) {
			if (getResizeMode().needResize(srcMemSize, mTargetMemSize)) {
				float scaleFactor = (float) Math.sqrt(((double) mTargetMemSize) / ((double) srcMemSize));
				int targetWidth = (int) (rawWidth * scaleFactor + 0.5F);
				int targetHeight = (int) (rawHeight * scaleFactor + 0.5F);
				setTargetSize(targetWidth, targetHeight);
				needResize = true;
			}
		}
		return needResize;
	}
}
