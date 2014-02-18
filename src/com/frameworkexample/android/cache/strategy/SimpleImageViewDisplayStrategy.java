package com.frameworkexample.android.cache.strategy;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class SimpleImageViewDisplayStrategy implements IDisplayStrategy {
	
	private static final SimpleImageViewDisplayStrategy sInstance = new SimpleImageViewDisplayStrategy();
	public static SimpleImageViewDisplayStrategy getInstance() {
		return sInstance;
	}
	
	private SimpleImageViewDisplayStrategy() { }
	
	@Override
	public void display(View view, Drawable drawable) {
		if (view instanceof ImageView) {
			ImageView iv = (ImageView) view;
			iv.setImageDrawable(drawable);
		} else {
			throw new IllegalArgumentException("the target view is an instance of "
					+ (null == view ? null : view.getClass() + ", but we want an ImageView here!"));
		}
	}
	
}
