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


	public BoundedViewHelper(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView);
			mMaxWidth = a.getDimensionPixelSize(R.styleable.BoundedView_boundedWidth,
					Integer.MAX_VALUE);
			mMaxHeight = a.getDimensionPixelSize(R.styleable.BoundedView_boundedHeight,
					Integer.MAX_VALUE);
			a.recycle();
		}
	}

	public Bound getData(int widthMeasureSpec, int width, int heightMeasureSpec, int height) {
		int mBoundedHeight = Math.min(height, mMaxHeight);
		int measuredHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		if (0 < mBoundedHeight && mBoundedHeight < measuredHeight) {
			int measureMode = View.MeasureSpec.getMode(heightMeasureSpec);
			heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mBoundedHeight, measureMode);
		}

		int mBoundedWidth = Math.min(width, mMaxWidth);
		int measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		if (0 < mBoundedWidth && mBoundedWidth < measuredWidth) {
			int measureMode = View.MeasureSpec.getMode(widthMeasureSpec);
			widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mBoundedWidth, measureMode);
		}

		return new Bound(mBoundedWidth, mBoundedHeight, widthMeasureSpec, heightMeasureSpec);
	}


	///// ПРИМЕР!
	//	@Override
	//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	//		BoundedViewHelper.Bound data =
	//				boundedHelper.getData(widthMeasureSpec, getMeasuredWidth(), heightMeasureSpec,
	//						getMeasuredHeight());
	//		setMeasuredDimension(data.boundedMeasuredWidth, data.boundedMeasuredHeight);
	//		super.onMeasure(data.widthMeasureSpec, data.heightMeasureSpec);
	//	}

	public class Bound {
		public final int boundedMeasuredWidth;
		public final int boundedMeasuredHeight;
		public final int widthMeasureSpec;
		public final int heightMeasureSpec;

		public Bound(int boundedMeasuredWidth, int boundedMeasuredHeight, int widthMeasureSpec,
					 int heightMeasureSpec) {
			this.boundedMeasuredWidth = boundedMeasuredWidth;
			this.boundedMeasuredHeight = boundedMeasuredHeight;
			this.widthMeasureSpec = widthMeasureSpec;
			this.heightMeasureSpec = heightMeasureSpec;
		}
	}


}