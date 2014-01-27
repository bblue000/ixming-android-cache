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
	
	public static <T extends View>IDisplayStrategy<T> simpleFadeInInstance(
			final IDisplayStrategy<T> other, final Drawable decor) {
		return new IDisplayStrategy<T>() {
			@Override
			public void display(T targetView, Drawable drawable) {
				Drawable defaultDecor = decor;
				if (null == decor) {
					defaultDecor = new ColorDrawable(Color.TRANSPARENT);
				}
				TransitionDrawable transitionDrawable = new TransitionDrawable(
					new Drawable[]{ defaultDecor, drawable });
				other.display(targetView, transitionDrawable);
				transitionDrawable.startTransition(500);
			}
		};
	}
	
}
