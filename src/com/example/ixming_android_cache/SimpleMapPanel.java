package com.example.ixming_android_cache;

import org.ixming.android.view.FilledInContainer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class SimpleMapPanel extends FilledInContainer {

	private FilledInContainer mContainer;
	public SimpleMapPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSimpleMapPanel();
	}

	public SimpleMapPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSimpleMapPanel();
	}

	public SimpleMapPanel(Context context) {
		super(context);
		initSimpleMapPanel();
	}
	
	private void initSimpleMapPanel() {
		// init root container
		mContainer = new FilledInContainer(getContext());
		mContainer.setLayoutParams(generateDefaultLayoutParams());
		addView(mContainer);
	}
	
	public void setBitmap(Bitmap bm) {
		mContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bm));
		requestLayout();
		invalidate();
	}
	
	public void setDrawable(Drawable drawable) {
		mContainer.setBackgroundDrawable(drawable);
		requestLayout();
		invalidate();
	}
	
	private void updateViewClip(View v, int width, int height) {
		LayoutParams lp = v.getLayoutParams();
		if (null == lp) {
			return ;
		}
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Drawable dr = mContainer.getBackground();
		if (null == dr) {
			return ;
		}
		int width = dr.getIntrinsicWidth();
		int height = dr.getIntrinsicHeight();
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				// change the pre-set parameters, no matter what is set previously,
				// we'd consider it filled in its parent
				updateViewClip(child, width, height);
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Drawable dr = mContainer.getBackground();
		if (null == dr) {
			return ;
		}
		int count = getChildCount();
		int paddLeft = getPaddingLeft();
		int paddTop = getPaddingTop();
		int width = dr.getIntrinsicWidth();
		int height = dr.getIntrinsicHeight();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				child.layout(paddLeft, paddTop, width + paddLeft, height + paddTop);
			}
		}
	}
	
}