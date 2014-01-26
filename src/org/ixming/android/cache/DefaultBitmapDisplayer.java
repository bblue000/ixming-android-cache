package org.ixming.android.cache;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

public class DefaultBitmapDisplayer implements IBitmapDisplayer<ImageView> {

	private final int ID_FIRST = 0;
	private final int ID_LAST = 1;
	private Drawable mDefDrawable = new ColorDrawable(Color.TRANSPARENT);
	private TransitionDrawable mTransitionDrawable;
	{
		mTransitionDrawable = new TransitionDrawable(new Drawable[]{
				mDefDrawable, mDefDrawable
		});
		mTransitionDrawable.setId(0, ID_FIRST);
		mTransitionDrawable.setId(1, ID_LAST);
	}
	
	public void setDefaultDrawable(Drawable drawable) {
		if (null != drawable) {
			mTransitionDrawable.setDrawableByLayerId(ID_FIRST, drawable);
			if (mDefDrawable == mTransitionDrawable.getDrawable(1)) {
				mTransitionDrawable.setDrawableByLayerId(ID_LAST, drawable);
			}
		}
	}

	@Override
	public void setDefaultDrawable(ImageView view) {
		view.setImageDrawable(mDefDrawable);
	}
	
	@Override
	public void setBitmap(ImageView view, Bitmap bitmap) {
		mTransitionDrawable.setDrawableByLayerId(ID_LAST, new BitmapDrawable(view.getResources(), bitmap));
		view.setImageDrawable(mTransitionDrawable);
		mTransitionDrawable.startTransition(600);
	}


}
