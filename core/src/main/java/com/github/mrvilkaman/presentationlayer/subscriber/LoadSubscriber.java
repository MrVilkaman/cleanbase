package com.github.mrvilkaman.presentationlayer.subscriber;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BindType;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;

/**
 * Created by root on 16.03.16.
 */
public class LoadSubscriber<V extends BaseView, T> extends ViewSubscriber<V, T> {

	private @Nullable IProgressState progressState;

	public LoadSubscriber() {
	}

	public LoadSubscriber(@Nullable IProgressState progressState) {
		this.progressState = progressState;
	}

	@Override
	public void onStart() {
		if (needProgress()) {
			if (progressState != null)
				progressState.showProgress();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onNext(T item) {
		V view = view();
		if (view instanceof BindType) {
			((BindType<T>) view).bind(item);
		}
	}

	@Override
	public void onError(Throwable e) {
		BaseView view = view();
		if (view == null)
			return;

		if (needProgress()) {
			if (progressState != null)
				progressState.hideProgress();
		}
		super.onError(e);
	}

	protected boolean needProgress() {
		return true;
	}

	@Override
	public void onCompleted() {
		super.onCompleted();
		if (needProgress()) {
			if (progressState != null)
				progressState.hideProgress();
		}
	}

	@Override
	public void setView(V view) {
		super.setView(view);
		//Todo Remove it
		setProgressState(view);
	}

	public void setProgressState(@Nullable IProgressState progressState) {
		if (this.progressState == null) {
			this.progressState = progressState;
		}
	}

}
