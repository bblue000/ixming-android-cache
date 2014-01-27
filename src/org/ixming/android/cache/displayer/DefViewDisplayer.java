package org.ixming.android.cache.displayer;

import org.ixming.android.cache.strategy.AbsViewDisplayStrategy;
import org.ixming.android.cache.strategy.DefBackgroundDisplayStrategy;

public class DefViewDisplayer extends AbsViewDisplayer {

	@Override
	public AbsViewDisplayStrategy getDecorStrategy() {
		return DefBackgroundDisplayStrategy.getInstance();
	}

	@Override
	public AbsViewDisplayStrategy setFailedStrategy() {
		return DefBackgroundDisplayStrategy.getInstance();
	}

	@Override
	public AbsViewDisplayStrategy setLoadedStrategy() {
		return DefBackgroundDisplayStrategy.getInstance();
	}

}
