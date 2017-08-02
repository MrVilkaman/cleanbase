package com.github.mrvilkaman.presentationlayer.subscriber;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.INeedProgressState;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;

public class StreamSubscriber<V extends BaseView, T extends DataErrorWrapper<D>, D>
		extends ViewSubscriber<V, T> implements INeedProgressState {

	public StreamSubscriber() {
		super();
	}

	public StreamSubscriber(@Nullable IProgressState progressState) {
		super(progressState);
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

		if (wrap.isProgress()) {
			showProgress();
		} else {
			hideProgress();
		}
	}


	@Override
	public void onComplete() {
		hideProgress();
	}
}
