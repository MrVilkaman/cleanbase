package com.github.mrvilkaman.presentationlayer.fragments.core;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.subscriber.ViewSubscriber;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


public class BasePresenter<V extends BaseView> {

	private V view;

	private CompositeDisposable compositeSubscription = new CompositeDisposable();

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
	public final void setThrowableResolver(ThrowableResolver throwableResolver) {
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
	protected final <T> void subscribeUI(Single<T> observable, ViewSubscriber<V,T>
			subscriber) {
		injectSubscriberDependencies(subscriber);
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread())
				.subscribeWith(subscriber));
	}

	@SuppressWarnings("unchecked")
	protected final <T> void subscribeUI(Maybe<T> observable, ViewSubscriber<V,T>
            subscriber) {
		injectSubscriberDependencies(subscriber);
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread())
				.subscribeWith(subscriber));
	}

	@SuppressWarnings("unchecked")
	protected final void subscribeUI(Completable observable, ViewSubscriber<V,?> subscriber) {
		injectSubscriberDependencies(subscriber);
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread())
				.subscribeWith(subscriber));
	}

//	@SuppressWarnings("unchecked")
//	protected final <T> void subscribeUI(Flowable observable, ResourceSubscriber subscriber) {
//		injectSubscriberDependencies(subscriber);
//		compositeSubscription.add((ResourceSubscriber) observable.observeOn(schedulersProvider.mainThread())
//				.subscribeWith(subscriber));
//	}

	protected final <T> void subscribeUI(Observable<T> observable, ViewSubscriber<V,T> subscriber) {
		injectSubscriberDependencies(subscriber);
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread())
				.subscribeWith(subscriber));
	}

	@SuppressWarnings("unchecked")

	private void injectSubscriberDependencies(Object subscriber) {
		if (subscriber instanceof ViewSubscriber) {
			ViewSubscriber viewSubscriber = (ViewSubscriber) subscriber;
			viewSubscriber.setView(view);
			viewSubscriber.setUiResolver(uiResolver);
			viewSubscriber.setThrowableResolver(throwableResolver);
		}

		if (subscriber instanceof INeedProgressState) {
			INeedProgressState loadSubscriber = (INeedProgressState) subscriber;
			loadSubscriber.setProgressState(view);
		}
	}
}
