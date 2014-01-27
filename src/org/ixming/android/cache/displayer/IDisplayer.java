package org.ixming.android.cache.displayer;

import org.ixming.android.cache.strategy.IDisplayStrategy;

import android.view.View;

/**
 * 
 * 显示者，对不同的《方面》提供不同的显示策略；
 * 
 * @see IDisplayStrategy
 * @author Yin Yong
 */
public interface IDisplayer<V extends View> {

	/**
	 * Bitmap未加载时，如果设置了显示一张默认的/装饰的Drawable，使用该“策略”设置图片
	 */
	public IDisplayStrategy<V> getDecorStrategy();
	
	/**
	 * Bitmap加载失败时，如果设置了显示不同的Drawable，使用该“策略”设置图片
	 */
	public IDisplayStrategy<V> setFailedStrategy();
	
	/**
	 * Bitmap加载成功时，显示图片
	 */
	public IDisplayStrategy<V> setLoadedStrategy();
	
}
