package com.github.mrvilkaman.presentationlayer.fragments.core;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
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

	public BasePresenter() {
	}

	@Inject
	public final void setSchedulersProvider(SchedulersProvider schedulersProvider) {
		this.schedulersProvider = schedulersProvider;
	}
	@Inject
	public final void setSchedulersProvider(UIResolver uiResolver) {
		this.uiResolver = uiResolver;
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

	protected final <T> void subscribe(Observable<T> observable) {
		subscribe(observable, new ViewSubscriber<>());
	}

	@SuppressWarnings("unchecked")
	protected final <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
		if (subscriber instanceof ViewSubscriber) {
			ViewSubscriber viewSubscriber = (ViewSubscriber) subscriber;
			viewSubscriber.setView(view);
			viewSubscriber.setUiResolver(uiResolver);
		}
		compositeSubscription.add(observable.subscribe(subscriber));
	}

	protected final <T> void subscribeUI(Observable<T> observable, Subscriber<T> subscriber) {
		subscribe(observable.observeOn(schedulersProvider.mainThread()), subscriber);
	}
}
