/**
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
 * <p/>
 * 
 * 本包中提供了部分通用的实现。
 */
package com.frameworkexample.android.cache.strategy;