package org.ixming.android.cache.displayer;

import org.ixming.android.cache.strategy.AbsImageViewDisplayStrategy;

import android.widget.ImageView;

/**
 * 
 * 这是个针对ImageView的“显示者”基类
 * 
 * @author Yin Yong
 */
public abstract class AbsImageViewDisplayer implements IDisplayer<ImageView> {
	@Override
	public abstract AbsImageViewDisplayStrategy getDecorStrategy();

	@Override
	public abstract AbsImageViewDisplayStrategy setFailedStrategy();

	@Override
	public abstract AbsImageViewDisplayStrategy setLoadedStrategy();
}
