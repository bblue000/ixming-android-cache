package org.ixming.android.cache.strategy;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class DefImageSrcDisplayStrategy extends AbsImageViewDisplayStrategy {

	private static final DefImageSrcDisplayStrategy mInstance = new DefImageSrcDisplayStrategy();
	public static DefImageSrcDisplayStrategy getInstance() {
		return mInstance;
	}
	
	@Override
	public void display(ImageView targetView, Drawable drawable) {
		targetView.setImageDrawable(drawable);
	}

}
