package com.github.mrvilkaman.presentationlayer.subscriber;

import android.util.Log;

import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
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
		BaseView view = view();
		if (view == null)
			return;
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
