package com.github.mrvilkaman.presentationlayer.utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by baoleduc on 26/07/16.
 */
public class BoundedLinearLayout extends LinearLayout {


	private final BoundedViewHelper boundedHelper;

	public BoundedLinearLayout(Context context) {
		this(context, null);
	}

	public BoundedLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		boundedHelper = new BoundedViewHelper(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int boundedMeasuredWidth =
				boundedHelper.getBoundedMeasuredWidth(widthMeasureSpec, getMeasuredWidth());
		int boundedMeasuredHeight =
				boundedHelper.getBoundedMeasuredHeight(heightMeasureSpec, getMeasuredHeight());

		setMeasuredDimension(boundedMeasuredWidth, boundedMeasuredHeight);
		super.onMeasure(
				boundedHelper.getBoundedMeasuredWidth2(widthMeasureSpec, getMeasuredWidth()),
				boundedHelper.getBoundedMeasuredHeight2(heightMeasureSpec, getMeasuredHeight()));
	}

}