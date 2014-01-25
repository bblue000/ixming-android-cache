package org.ixming.android.cache;

/**
 * 如果使用该工具中的一些方法——如 {@link SimpleBitmapScaler} 等类中的方法，
 * 需要使用到这个类。
 * <p/>
 * 该类包含了一些参数设定，概念区分等。
 * <p/>
 * 通过 {@link #obtain()} 方法获取实例。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public class BitmapTransition {

	private static ThreadLocal<BitmapTransition> sInstanceCaches = new ThreadLocal<BitmapTransition>() {
		protected BitmapTransition initialValue() {
			return new BitmapTransition();
		}
	};
	
	public static BitmapTransition obtain() {
		return sInstanceCaches.get().reset();
	}

	private ScaleMode mScaleMode = ScaleMode.AlwaysIfDiffer;
	private boolean mRecycleSrc = false;
	/*package*/ BitmapTransition() {
		
	}
	
	/*package*/ BitmapTransition reset() {
		mScaleMode = ScaleMode.AlwaysIfDiffer;
		mRecycleSrc = false;
		return this;
	}
	
	/**
	 * 该方法用于指定转变Bitmap大小的模式。
	 * 
	 * @see {@link ScaleMode}
	 */
	public BitmapTransition setScaleMode(ScaleMode mode) {
		if (null != mode) {
			mScaleMode = mode;
		}
		return this;
	}
	
	/**
	 * 该方法用于指定转变Bitmap后是否回收（recycle）原Bitmap。
	 */
	public BitmapTransition setRecycleSrc(boolean recycleSrc) {
		mRecycleSrc = recycleSrc;
		return this;
	}
	
	public ScaleMode getScaleMode() {
		return mScaleMode;
	}
	
	public boolean getRecycleSrc() {
		return mRecycleSrc;
	}
}
