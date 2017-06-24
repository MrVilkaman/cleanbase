package com.github.mrvilkaman.presentationlayer.subscriber;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.INeedProgressState;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;

public class StreamSubscriber<V extends BaseView, T extends DataErrorWrapper<D>, D>
		extends ViewSubscriber<V, T> implements INeedProgressState {

	private @Nullable IProgressState progressState;

	public StreamSubscriber() {
	}

	public StreamSubscriber(@Nullable IProgressState progressState) {
		this.progressState = progressState;
	}

	public void onNextValue(D value) {

	}

	@Override
	public void onNext(T wrap) {
		if (wrap.isSuccess()) {
			onNextValue(wrap.getValue());
		}
		if (wrap.isError()) {
			onError(wrap.getThrowable());
		}

		if (progressState != null) {
			if (wrap.isProgress()) {
				progressState.showProgress();
			} else {
				progressState.hideProgress();
			}
		}
	}

	@Override
	public void setProgressState(@Nullable IProgressState progressState) {
		if (this.progressState == null) {
			this.progressState = progressState;
		}
	}

	@Override
	public void onError(Throwable e) {
		if (progressState != null) {
			progressState.hideProgress();
		}
		super.onError(e);
	}

	@Override
	public void onCompleted() {
		if (progressState != null) {
			progressState.hideProgress();
		}
	}
}
