package com.github.mrvilkaman.namegenerator.presentationlayer.utils;

import com.github.mrvilkaman.namegenerator.datalayer.subscriber.DefaultSubscriber;
import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BaseView;

import java.lang.ref.WeakReference;

/**
 * Created by root on 16.03.16.
 */
public class LoadSubscriber<T> extends DefaultSubscriber<T> {
	private WeakReference<BaseView> viewRef;

	public LoadSubscriber(BaseView view) {
		this.viewRef = new WeakReference<>(view);
	}

	@Override
	public void onError(Throwable e) {
		BaseView view = viewRef.get();
		view.hideProgress();
		view.showError(e);
	}

	@Override
	public void onCompleted() {
		viewRef.get()
				.hideProgress();
	}
}
