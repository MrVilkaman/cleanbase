package com.github.mrvilkaman.presentationlayer.fragments.core;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.presentationlayer.subscriber.ViewSubscriber;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends BaseView> {

	private V view;

	private CompositeSubscription compositeSubscription = new CompositeSubscription();

	private SchedulersProvider schedulersProvider;

	public BasePresenter() {
	}

	@Inject
	public void setSchedulersProvider(SchedulersProvider schedulersProvider) {
		this.schedulersProvider = schedulersProvider;
	}

	public void onViewAttached() {
	}

	public void onViewDetached() {
		compositeSubscription.clear();
	}

	public final V view() {
		return view;
	}

	public final void setView(V view) {
		this.view = view;
	}

	protected final <T> void subscribe(Observable<T> observable) {
		subscribe(observable, new ViewSubscriber<>());
	}

	protected final <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
		if (subscriber instanceof ViewSubscriber) {
			((ViewSubscriber) subscriber).setView(view);
		}
		compositeSubscription.add(observable.subscribe(subscriber));
	}

	protected final <T> void subscribeUI(Observable<T> observable, Subscriber<T> subscriber) {
		subscribe(observable.observeOn(schedulersProvider.mainThread()), subscriber);
	}
}
