package com.frameworkexample.android.cache.strategy;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * “显示策略”的接口；
 * 
 * <p/>
 * 
 * 显示“策略”，是如何将图片设置到View中的一种抽象；
 * 
 * <p/>
 * 
 * 如：
 * 1、 {@code android.view.View} 的 {@link android.view.View#setBackgroundDrawable(android.graphics.drawable.Drawable)} ；<br/>
 * 2、 {@code android.widget.ImageView} 的 {@link android.widget.ImageView#setImageDrawable(android.graphics.drawable.Drawable)} ；<br/>
 * 
 * 以及其他用户自定义的设置图片的方式。
 * 
 * @author Yin Yong
 */
public interface IDisplayStrategy {

	/**
	 * 将 {@code drawable} 设置到相应的View中显示
	 */
	public void display(View view, Drawable drawable);
	
}
