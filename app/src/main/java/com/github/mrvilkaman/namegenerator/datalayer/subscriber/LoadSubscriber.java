package com.github.mrvilkaman.namegenerator.datalayer.subscriber;

import android.util.Log;

import com.github.mrvilkaman.namegenerator.BuildConfig;
import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BaseView;

import java.lang.ref.WeakReference;


/**
 * Created by root on 16.03.16.
 */
public class LoadSubscriber<V extends BaseView, T> extends DefaultSubscriber<T> {
	private WeakReference<V> viewRef;

	public LoadSubscriber(V view) {
		this.viewRef = new WeakReference<>(view);
		if (needProgress()) {
			view().showProgress();
		}
	}

	@Override
	public void onError(Throwable e) {
		super.onError(e);
		BaseView view = view();
		if (needProgress()) {
			view.hideProgress();
		}
		if (showError()) {
			view.showError(e);
		}
		if (BuildConfig.DEBUG) {
			Log.e("LoadSubscriber", "onError", e);
		}
	}

	protected boolean needProgress() {
		return true;
	}

	protected boolean showError() {
		return true;
	}

	@Override
	public void onCompleted() {
		super.onCompleted();
		if (needProgress()) {
			view().hideProgress();
		}
	}

	protected V view() {
		return viewRef.get();
	}
}
