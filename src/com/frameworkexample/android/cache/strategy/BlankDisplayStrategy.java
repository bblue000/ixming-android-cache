package com.frameworkexample.android.cache.strategy;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 什么都不做的“显示策略”
 * 
 * @author Yin Yong
 */
public class BlankDisplayStrategy implements IDisplayStrategy {

	private static final BlankDisplayStrategy sInstance = new BlankDisplayStrategy();
	public static BlankDisplayStrategy getInstance() {
		return sInstance;
	}
	
	private BlankDisplayStrategy() { }
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p/>
	 * 
	 * but here we do nothing
	 */
	@Override
	public void display(View view, Drawable drawable) {
		//TODO do nothing
	}

}
