package com.github.mrvilkaman.presentationlayer.fragments.core;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.subscriber.ViewSubscriber;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends BaseView> {

	private V view;

	private CompositeSubscription compositeSubscription = new CompositeSubscription();

	private SchedulersProvider schedulersProvider;
	private UIResolver uiResolver;
	private ThrowableResolver throwableResolver;

	public BasePresenter() {
	}

	@Inject
	public final void setSchedulersProvider(SchedulersProvider schedulersProvider) {
		this.schedulersProvider = schedulersProvider;
	}

	@Inject
	public final void setUIResolver(UIResolver uiResolver) {
		this.uiResolver = uiResolver;
	}

	@Inject
	public void setThrowableResolver(ThrowableResolver throwableResolver) {
		this.throwableResolver = throwableResolver;
	}

	public void onViewAttached() {
	}

	public void onViewDetached() {
		compositeSubscription.clear();
	}

	public final V view() {
		return view;
	}

	public final UIResolver uiResolver() {
		return uiResolver;
	}

	public final void setView(V view) {
		this.view = view;
	}

	@SuppressWarnings("unchecked")
	protected final <T> void subscribeUI(Observable<T> observable, Subscriber<T> subscriber) {
		if (subscriber instanceof ViewSubscriber) {
			ViewSubscriber viewSubscriber = (ViewSubscriber) subscriber;
			viewSubscriber.setView(view);
			viewSubscriber.setUiResolver(uiResolver);
			viewSubscriber.setThrowableResolver(throwableResolver);
		}
		if (subscriber instanceof INeedProgressState) {
			INeedProgressState loadSubscriber = (INeedProgressState) subscriber;
			if (view instanceof IProgressState) {
				loadSubscriber.setProgressState((IProgressState) view);
			}
		}
		compositeSubscription.add(
				observable.observeOn(schedulersProvider.mainThread()).subscribe(subscriber));

	}
}
