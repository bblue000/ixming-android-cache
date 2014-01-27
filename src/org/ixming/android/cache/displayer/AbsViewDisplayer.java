package org.ixming.android.cache.displayer;

import org.ixming.android.cache.strategy.AbsViewDisplayStrategy;

import android.view.View;

/**
 * 
 * 这是个针对View的“显示者”基类
 * 
 * @author Yin Yong
 */
public abstract class AbsViewDisplayer implements IDisplayer<View> {
	@Override
	public abstract AbsViewDisplayStrategy getDecorStrategy();

	@Override
	public abstract AbsViewDisplayStrategy setFailedStrategy();

	@Override
	public abstract AbsViewDisplayStrategy setLoadedStrategy();
}
