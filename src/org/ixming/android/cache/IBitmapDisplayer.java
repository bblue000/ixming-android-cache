package org.ixming.android.cache;

import android.graphics.Bitmap;
import android.view.View;

public interface IBitmapDisplayer<T extends View> {

	public void setDefaultDrawable(T view);
	
	public void setBitmap(T view, Bitmap bitmap);
	
}
