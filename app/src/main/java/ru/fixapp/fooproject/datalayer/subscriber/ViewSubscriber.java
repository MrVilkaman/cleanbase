package ru.fixapp.fooproject.datalayer.subscriber;

import android.util.Log;

import java.lang.ref.WeakReference;

import ru.fixapp.fooproject.BuildConfig;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseView;


/**
 * Created by root on 15.03.16.
 */
public abstract class ViewSubscriber<V extends BaseView, T> extends rx.Subscriber<T> {

	private final WeakReference<V> viewRef;

	public ViewSubscriber(V view) {
		this.viewRef = new WeakReference<>(view);
	}

	@Override
	public void onError(Throwable e) {
		if (BuildConfig.DEBUG) {
			Log.e("LoadSubscriber", "onError", e);
		}
	}

	@Override
	public void onCompleted() {

	}

	protected V view() {
		return viewRef.get();
	}
}
