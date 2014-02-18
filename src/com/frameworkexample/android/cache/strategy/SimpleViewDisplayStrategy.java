package com.frameworkexample.android.cache.strategy;

import android.graphics.drawable.Drawable;
import android.view.View;

public class SimpleViewDisplayStrategy implements IDisplayStrategy {
	
	private static final SimpleViewDisplayStrategy sInstance = new SimpleViewDisplayStrategy();
	public static SimpleViewDisplayStrategy getInstance() {
		return sInstance;
	}
	
	private SimpleViewDisplayStrategy() { }
	
	public void display(View view, Drawable drawable) {
		if (null != view) {
			view.setBackgroundDrawable(drawable);
		}
	}
	
}
