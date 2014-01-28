package org.ixming.android.cache.strategy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;

/**
 * 获取一些辅助策略，附加一些简单的效果
 * 
 * @author Yin Yong
 */
public class DisplayStrategyFactory {

	private DisplayStrategyFactory() { }
	
	private static final Drawable TRANSPARENT_DRAWABLE = new ColorDrawable(Color.TRANSPARENT);
	
	public static IDisplayStrategy simpleFadeInInstance(
			final IDisplayStrategy other, final Drawable decor, final boolean crossFade) {
		return new IDisplayStrategy() {
			@Override
			public void display(View view, Drawable drawable) {
				Drawable defaultDecor = decor;
				if (null == defaultDecor) {
					defaultDecor = TRANSPARENT_DRAWABLE;
				}
				if (null == drawable) {
					drawable = TRANSPARENT_DRAWABLE;
				}
				TransitionDrawable transitionDrawable = new TransitionDrawable(
					new Drawable[]{ defaultDecor, drawable });
				transitionDrawable.setCrossFadeEnabled(crossFade);
				other.display(view, transitionDrawable);
				transitionDrawable.startTransition(500);
			}
		};
	}
	
}
