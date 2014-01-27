package org.ixming.android.cache.displayer;

import org.ixming.android.cache.strategy.IDisplayStrategy;

import android.view.View;

/**
 * 
 * 这是个统一的显示者对象，“统一”的意思是，所有的“显示策略”都是公用的
 * 
 * @author Yin Yong
 */
public class UniformDisplayer<V extends View> implements IDisplayer<V> {

	private IDisplayStrategy<V> mStrategy;
	/**
	 * 创建一个空的UniformBitmapDisplayer对象
	 */
	public UniformDisplayer() {
	}
	
	/**
	 * 创建一个空的UniformBitmapDisplayer对象
	 */
	public UniformDisplayer(IDisplayStrategy<V> strategy) {
		mStrategy = strategy;
	}
	
	public void setDisplayStrategy(IDisplayStrategy<V> strategy) {
		mStrategy = strategy;
	}
	
	/**
	 * 获得统一设置的 IDisplayStrategy 
	 */
	public IDisplayStrategy<V> getDisplayStrategy() {
		return mStrategy;
	}
	
	@Override
	public IDisplayStrategy<V> getDecorStrategy() {
		return getDisplayStrategy();
	}

	@Override
	public IDisplayStrategy<V> setFailedStrategy() {
		return getDisplayStrategy();
	}

	@Override
	public IDisplayStrategy<V> setLoadedStrategy() {
		return getDisplayStrategy();
	}

}
