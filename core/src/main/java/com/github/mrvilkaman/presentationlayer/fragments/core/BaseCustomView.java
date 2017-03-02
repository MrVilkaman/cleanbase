package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public abstract class BaseCustomView extends FrameLayout implements BaseView {

	public BaseCustomView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater mInflater =
				(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(getLayoutId(), this, true);
		onViewCreate();
	}

	protected abstract void onViewCreate();

	protected abstract int getLayoutId();

	@Override
	public void handleError(Throwable throwable) {

	}
}
