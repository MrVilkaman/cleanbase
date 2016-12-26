package com.github.mrvilkaman.presentationlayer.subscriber;

import android.util.Log;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.utils.AppUtils;
import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;

import java.lang.ref.WeakReference;


/**
 * Created by root on 15.03.16.
 */
public class ViewSubscriber<V extends BaseView, T> extends rx.Subscriber<T> {

	private final WeakReference<V> viewRef;
	private final String string;
	private boolean skipNextError;

	public ViewSubscriber(V view) {
		this.viewRef = new WeakReference<>(view);
		// TODO: 25.12.16 mode to global setting
		string = CleanBaseSettings.needSubscribeLogs() ? AppUtils.getSubscriberStartStack() : "";
	}

	@Override
	public void onError(Throwable e) {
		// TODO: 25.12.16 mode to global setting

		if (CleanBaseSettings.needSubscribeLogs()) {
			Log.e("LoadSubscriber", "Start by:" + string, e);
		}
		BaseView view = view();
		if (view == null) return;
		if (showError()) {
			view.handleError(e);
		}
	}

	@Override
	public void onNext(T t) {

	}

	@Override
	public void onCompleted() {

	}

	protected V view() {
		return viewRef.get();
	}

	protected void skipNextError() {
		skipNextError = true;
	}

	protected boolean showError() {
		return !skipNextError;
	}

}
