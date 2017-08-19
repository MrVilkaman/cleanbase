package com.github.mrvilkaman.presentationlayer.subscriber;

import android.support.annotation.Nullable;
import android.util.Log;

import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.INeedProgressState;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by root on 15.03.16.
 */
@SuppressWarnings("WeakerAccess")
public class ViewSubscriber<V extends BaseView, T> extends DisposableObserver<T>
		implements MaybeObserver<T>, Observer<T>, SingleObserver<T>,
		CompletableObserver, Disposable,
		IProgressState, INeedProgressState {

	private final String string;
	private V view;
	private UIResolver uiResolver;
	private ThrowableResolver throwableResolver;

	private @Nullable IProgressState progressState;

	private boolean skipNextError;

	public ViewSubscriber() {
		this(null);
	}

	public ViewSubscriber(@Nullable IProgressState progressState) {
		this.progressState = progressState;
		string = CleanBaseSettings.needSubscribeLogs() ? DevUtils.getSubscriberStartStack() : "";

	}

	public final void setView(V view) {
		this.view = view;
	}


	@Override
	public final void onSuccess(@NonNull T t) {
		onNext(t);
		onComplete();
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
	public void onComplete() {

	}

	@Override
	public void onNext(T t) {

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

	public void setProgressState(@Nullable IProgressState progressState) {
		if (this.progressState == null) {
			this.progressState = progressState;
		}
	}


	@Override
	public void showProgress() {
		if (progressState != null)
			progressState.showProgress();
	}

	@Override
	public void hideProgress() {
		if (progressState != null)
			progressState.hideProgress();
	}
}
