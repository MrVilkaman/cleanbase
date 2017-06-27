package com.github.mrvilkaman.presentationlayer.subscriber;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BindType;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;

/**
 * Created by root on 16.03.16.
 */
public class LoadSubscriber<V extends BaseView, T> extends ViewSubscriber<V, T>{


	public LoadSubscriber() {
	}

	public LoadSubscriber(@Nullable IProgressState progressState) {
		super(progressState);
	}

	@Override
	public void onStart() {
		if (needProgress()) {
			showProgress();
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

		if (needProgress()) {
			hideProgress();
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
			hideProgress();
		}
	}
}
