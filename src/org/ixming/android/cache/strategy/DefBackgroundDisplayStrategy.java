package org.ixming.android.cache.strategy;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 设置背景图片的策略
 * 
 * @author Yin Yong
 */
public class DefBackgroundDisplayStrategy extends AbsViewDisplayStrategy {

	private static final DefBackgroundDisplayStrategy mInstance = new DefBackgroundDisplayStrategy();
	public static DefBackgroundDisplayStrategy getInstance() {
		return mInstance;
	}
	
	private DefBackgroundDisplayStrategy() { }
	
	@Override
	public void display(View targetView, Drawable drawable) {
		targetView.setBackgroundDrawable(drawable);
	}
	
}
