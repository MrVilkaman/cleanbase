package com.github.mrvilkaman.presentationlayer.utils.ui;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.github.mrvilkaman.core.R;


/**
 * Created by baoleduc on 26/07/16.
 */
public class BoundedViewHelper {
	public static final int MODE_MIN = 0;
	public static final int MODE_CALC = 1;
	private int mMaxWidth = Integer.MAX_VALUE;
	private int mMaxHeight = Integer.MAX_VALUE;
	private int mode;


	public BoundedViewHelper(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView);
			mMaxWidth = a.getDimensionPixelSize(R.styleable.BoundedView_boundedWidth,
					Integer.MAX_VALUE);
			mMaxHeight = a.getDimensionPixelSize(R.styleable.BoundedView_boundedHeight,
					Integer.MAX_VALUE);
			mode = a.getInt(R.styleable.BoundedView_mode, 0);
			a.recycle();
		}
	}

	public int getBoundedMeasuredWidth(int widthMeasureSpec, int width) {
		switch (mode) {
			case MODE_MIN:
				return Math.min(width, mMaxWidth);
			case MODE_CALC:
				return getBoundedMeasuredWidth2(widthMeasureSpec, width);
			default:
				return width;
		}
	}

	public int getBoundedMeasuredHeight(int heightMeasureSpec, int height) {
		switch (mode) {
			case MODE_MIN:
				return Math.min(height, mMaxHeight);
			case MODE_CALC:
				return getBoundedMeasuredHeight2(heightMeasureSpec, height);
			default:
				return height;
		}
	}

	public int getMode() {
		return mode;
	}

	public int getBoundedMeasuredWidth2(int widthMeasureSpec, int width) {
		int mBoundedWidth = Math.min(width, mMaxWidth);
		int measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		if (0 < mBoundedWidth && mBoundedWidth < measuredWidth) {
			int measureMode = View.MeasureSpec.getMode(widthMeasureSpec);
			widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mBoundedWidth, measureMode);
		}

		return widthMeasureSpec;
	}

	public int getBoundedMeasuredHeight2(int heightMeasureSpec, int height) {
		int mBoundedHeight = Math.min(height, mMaxHeight);
		int measuredHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		if (0 < mBoundedHeight && mBoundedHeight < measuredHeight) {
			int measureMode = View.MeasureSpec.getMode(heightMeasureSpec);
			heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mBoundedHeight, measureMode);
		}
		return heightMeasureSpec;
	}


}