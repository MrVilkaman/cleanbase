package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.utils.UIUtils;

public abstract class BaseCustomView<P extends BasePresenter> extends FrameLayout
		implements BaseView {


	private View progressBar;
	private BaseView parrentView;

	private P presenter;

	public BaseCustomView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater mInflater =
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(getLayoutId(), this, true);
		onViewCreate();
		progressBar = findViewById(R.id.progress_wheel);
	}

	protected abstract void onViewCreate();

	protected abstract int getLayoutId();

	public void setParrentView(BaseView view) {
		parrentView = view;
	}

	@Override
	public void hideProgress() {
		if (progressBar != null) {
			UIUtils.changeVisibility(progressBar, false);
		} else {
			parrentView.hideProgress();
		}
	}

	@Override
	public void showProgress() {
		if (progressBar != null) {
			UIUtils.changeVisibility(progressBar, true);
		} else {
			parrentView.showProgress();
		}
	}

	@Override
	public void handleError(Throwable throwable) {
		parrentView.handleError(throwable);
	}

	public P getPresenter() {
		return presenter;
	}

	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}
}
