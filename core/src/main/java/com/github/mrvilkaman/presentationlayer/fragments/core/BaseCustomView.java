package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.utils.UIUtils;

import javax.inject.Inject;

public abstract class BaseCustomView<P extends BasePresenter> extends FrameLayout
		implements BaseView {

	@Inject P presenter;
	private View progressBar;
	private BaseView parentView;

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
		View inflate = mInflater.inflate(getLayoutId(), this, false);
		onViewCreate(inflate,context,attrs);
		progressBar = inflate.findViewById(getProgressWheelId());
		addView(inflate);
	}

	protected int getProgressWheelId() {
		return R.id.progress_wheel_widget;
	}

	protected abstract void onViewCreate(View inflate, Context context, AttributeSet attrs);

	protected abstract int getLayoutId();

	@Override
	public void hideProgress() {
		if (progressBar != null) {
			UIUtils.changeVisibility(progressBar, false);
		} else {
			parentView.hideProgress();
		}
	}

	@Override
	public void showProgress() {
		if (progressBar != null) {
			UIUtils.changeVisibility(progressBar, true);
		} else {
			parentView.showProgress();
		}
	}

	@Override
	public void handleError(Throwable throwable) {
		parentView.handleError(throwable);
	}

	public P getPresenter() {
		return presenter;
	}

	public <T> T getParentView(Class<T> tClass) {
		return tClass.cast(parentView);
	}

	@SuppressWarnings("unchecked")
	public void bind(BaseView view) {
		parentView = view;
		if (presenter != null)
			presenter.setView(this);
	}

}
