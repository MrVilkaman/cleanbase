package ru.fixapp.fooproject.datalayer.subscriber;

import android.util.Log;

import java.lang.ref.WeakReference;

import ru.fixapp.fooproject.BuildConfig;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseView;
import ru.fixapp.fooproject.presentationlayer.utils.AppUtils;


/**
 * Created by root on 15.03.16.
 */
public class ViewSubscriber<V extends BaseView, T> extends rx.Subscriber<T> {

	private final WeakReference<V> viewRef;
	private final String string;

	public ViewSubscriber(V view) {
		this.viewRef = new WeakReference<>(view);
		string = BuildConfig.DEBUG ? AppUtils.getSubscriberStartStack() : "";
	}

	@Override
	public void onError(Throwable e) {
		if (BuildConfig.DEBUG) {
			Log.e("LoadSubscriber", "Start by:" + string, e);
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
}
