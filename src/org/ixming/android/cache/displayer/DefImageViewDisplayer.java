package org.ixming.android.cache.displayer;

import org.ixming.android.cache.strategy.AbsImageViewDisplayStrategy;
import org.ixming.android.cache.strategy.DefImageSrcDisplayStrategy;

public class DefImageViewDisplayer extends AbsImageViewDisplayer {

	public DefImageViewDisplayer() {
	}

	@Override
	public AbsImageViewDisplayStrategy getDecorStrategy() {
		return DefImageSrcDisplayStrategy.getInstance();
	}

	@Override
	public AbsImageViewDisplayStrategy setFailedStrategy() {
		return DefImageSrcDisplayStrategy.getInstance();
	}

	@Override
	public AbsImageViewDisplayStrategy setLoadedStrategy() {
		return DefImageSrcDisplayStrategy.getInstance();
	}

}
