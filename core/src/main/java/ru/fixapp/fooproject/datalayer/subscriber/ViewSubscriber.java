package ru.fixapp.fooproject.datalayer.subscriber;

import android.util.Log;

import com.github.mrvilkaman.core.BuildConfig;

import java.lang.ref.WeakReference;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseView;
import ru.fixapp.fooproject.presentationlayer.utils.AppUtils;


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
		string = BuildConfig.DEBUG ? AppUtils.getSubscriberStartStack() : "";
	}

	@Override
	public void onError(Throwable e) {
		// TODO: 25.12.16 mode to global setting

		if (BuildConfig.DEBUG) {
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
