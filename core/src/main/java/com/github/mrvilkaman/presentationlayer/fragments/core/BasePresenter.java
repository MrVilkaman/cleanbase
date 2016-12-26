package com.github.mrvilkaman.presentationlayer.fragments.core;

import javax.inject.Inject;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
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
	}

	public final V view() {
		return view;
	}

	public final void setView(V view) {
		this.view = view;
	}

	protected <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
		compositeSubscription.add(observable.subscribe(subscriber));
	}

	protected <T> void subscribeUI(Observable<T> observable, Subscriber<T> subscriber) {
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread())
				.subscribe(subscriber));
	}
}
