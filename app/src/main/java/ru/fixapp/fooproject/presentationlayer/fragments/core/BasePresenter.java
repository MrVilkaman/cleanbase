package ru.fixapp.fooproject.presentationlayer.fragments.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.domainlayer.usecase.core.UseCase;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends BaseView> {

	private V view;

	private CompositeSubscription compositeSubscription = new CompositeSubscription();

	private List<UseCase> useCaseList = new ArrayList<>();
	private SchedulersProvider schedulersProvider;

	public BasePresenter(SchedulersProvider schedulersProvider) {
		this.schedulersProvider = schedulersProvider;
	}

	protected void addUseCases(UseCase... useCases) {
		useCaseList.addAll(Arrays.asList(useCases));
	}

	public void onViewAttached() {
	}

	public void onViewDetached() {
		for (UseCase useCase : useCaseList) {
			useCase.unsubscribe();
		}
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
		compositeSubscription.add(observable.observeOn(schedulersProvider.mainThread()).subscribe(subscriber));
	}
}
