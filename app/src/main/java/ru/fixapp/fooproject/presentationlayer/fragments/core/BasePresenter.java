package ru.fixapp.fooproject.presentationlayer.fragments.core;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.domainlayer.usecase.core.UseCase;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V extends BaseView> {

	private V view;

	private CompositeSubscription compositeSubscription;

	private List<UseCase> useCaseList = new ArrayList<>();
	private SchedulersProvider schedulersProvider;

	public BasePresenter(SchedulersProvider schedulersProvider) {
		this.schedulersProvider = schedulersProvider;
	}

	protected void addUseCases(UseCase... useCases){
		useCaseList.addAll(Arrays.asList(useCases));
	}

	protected void onViewAttached() {
		compositeSubscription = new CompositeSubscription();
	}

	protected void onViewDetached() {
		for (UseCase useCase : useCaseList) {
			useCase.unsubscribe();
		}
	}

	protected void onViewBeforeDetached() {
	}

	public final V view() {
		return view;
	}

	public final void setView(V view) {
		if (view == null) {
						if (this.view != null) {
				onViewBeforeDetached();
			}
			this.view = null;
			onViewDetached();
		} else {
			this.view = view;
			onViewAttached();
		}
	}

	public final Context getContext() {
		return view == null ? null : view.getContext();
	}

	protected <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
		compositeSubscription.add(observable.subscribe(subscriber));
	}

	protected <T> void subscribeUI(Observable<T> observable, Subscriber<T> subscriber) {
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread()).subscribe(subscriber));
	}
}
