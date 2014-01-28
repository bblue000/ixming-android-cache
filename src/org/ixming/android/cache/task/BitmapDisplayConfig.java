package org.ixming.android.cache.task;

import org.ixming.android.cache.strategy.BlankDisplayStrategy;
import org.ixming.android.cache.strategy.IDisplayStrategy;
import org.ixming.android.cache.utils.BitmapTransition;

import android.graphics.drawable.Drawable;

public class BitmapDisplayConfig {

	private final IDisplayStrategy mDefaultStrategy = BlankDisplayStrategy.getInstance();
	private String mUrl;

	private Drawable mDecorDrawable;
	private Drawable mFailedDrawable;
	
	// 是否需要改变宽高大小
	private BitmapTransition mTransition;

	private IDisplayStrategy mDecorDisplayStrategy = mDefaultStrategy;
	private IDisplayStrategy mFailedDisplayStrategy = mDefaultStrategy;
	private IDisplayStrategy mLoadedDisplayStrategy = mDefaultStrategy;

	public String getUrl() {
		return mUrl;
	}

	public BitmapDisplayConfig setUrl(String url) {
		this.mUrl = url;
		return this;
	}

	public BitmapTransition getTransition() {
		return mTransition;
	}

	public BitmapDisplayConfig setTransition(BitmapTransition transition) {
		this.mTransition = transition;
		return this;
	}

	public IDisplayStrategy getDecorDisplayStrategy() {
		return mDecorDisplayStrategy;
	}

	public BitmapDisplayConfig setDecorDisplayStrategy(IDisplayStrategy decorDisplayStrategy) {
		if (null == decorDisplayStrategy) {
			decorDisplayStrategy = mDefaultStrategy;
		}
		this.mDecorDisplayStrategy = decorDisplayStrategy;
		return this;
	}

	public IDisplayStrategy getFailedDisplayStrategy() {
		return mFailedDisplayStrategy;
	}

	public BitmapDisplayConfig setFailedDisplayStrategy(IDisplayStrategy failedDisplayStrategy) {
		if (null == failedDisplayStrategy) {
			failedDisplayStrategy = mDefaultStrategy;
		}
		this.mFailedDisplayStrategy = failedDisplayStrategy;
		return this;
	}

	public IDisplayStrategy getLoadedDisplayStrategy() {
		return mLoadedDisplayStrategy;
	}

	public BitmapDisplayConfig setLoadedDisplayStrategy(IDisplayStrategy loadedDisplayStrategy) {
		if (null == loadedDisplayStrategy) {
			loadedDisplayStrategy = mDefaultStrategy;
		}
		this.mLoadedDisplayStrategy = loadedDisplayStrategy;
		return this;
	}

	public Drawable getDecorDrawable() {
		return mDecorDrawable;
	}

	public BitmapDisplayConfig setDecorDrawable(Drawable decorDrawable) {
		this.mDecorDrawable = decorDrawable;
		return this;
	}

	public Drawable getFailedDrawable() {
		return mFailedDrawable;
	}

	public BitmapDisplayConfig setFailedDrawable(Drawable failedDrawable) {
		this.mFailedDrawable = failedDrawable;
		return this;
	}

}
