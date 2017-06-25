package com.github.mrvilkaman.presentationlayer.subscriber;

import android.util.Log;

import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;


/**
 * Created by root on 15.03.16.
 */
@SuppressWarnings("WeakerAccess")
public class ViewSubscriber<V extends BaseView, T> extends rx.Subscriber<T> {

	private final String string;
	private V view;
	private UIResolver uiResolver;
	private ThrowableResolver throwableResolver;
	private boolean skipNextError;

	public ViewSubscriber() {
		string = CleanBaseSettings.needSubscribeLogs() ? DevUtils.getSubscriberStartStack() : "";
	}

	public void setView(V view) {
		this.view = view;
	}

	@Override
	public void onError(Throwable e) {
		if (CleanBaseSettings.needSubscribeLogs()) {
			Log.e("ViewSubscriber", "Start by:" + string, e);
		}
		if (throwableResolver != null && showError()) {
			throwableResolver.handleError(e);
		}
	}

	@Override
	public void onNext(T t) {

	}

	@Override
	public void onCompleted() {

	}

	public void setThrowableResolver(ThrowableResolver throwableResolver) {
		this.throwableResolver = throwableResolver;
	}

	protected V view() {
		return view;
	}

	protected UIResolver getUiResolver() {
		return uiResolver;
	}

	public void setUiResolver(UIResolver uiResolver) {
		this.uiResolver = uiResolver;
	}

	protected void skipNextError() {
		skipNextError = true;
	}

	protected boolean showError() {
		return !skipNextError;
	}
}
