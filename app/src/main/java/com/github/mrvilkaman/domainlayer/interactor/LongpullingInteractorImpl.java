package com.github.mrvilkaman.domainlayer.interactor;

import android.util.Log;

import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class LongpullingInteractorImpl implements LongpullingInteractor {

	private final SchedulersProvider schedulersProvider;
	private final GlobalSubscriptionManager subscribtionManager;
	private final CompositeSubscription subscription;

	public LongpullingInteractorImpl(SchedulersProvider schedulersProvider,
									 GlobalSubscriptionManager subscribtionManager) {
		this.schedulersProvider = schedulersProvider;
		this.subscribtionManager = subscribtionManager;

		subscription = new CompositeSubscription();
	}

	@Override
	public Observable<String> doWorkWithError() {
		Log.d("QWER", "doWorkWithError start");
		Observable<String> qwer = Observable.just("")
				.delay(2, TimeUnit.SECONDS)
				.flatMap(s -> Observable.error(new NotFoundException()))
				.map(o -> "")
				.doOnNext(s -> Log.d("QWER", "doWorkWithError doOnNext"))
				.subscribeOn(schedulersProvider.io())
				.doOnError(throwable -> Log.d("QWER", "doWorkWithError doOnError"));
		return subscribtionManager.subscribeWithResult(qwer);

	}

	@Override
	public void doWork() {
		Log.d("QWER", "doWork start");

		Observable<String> qwer = Observable.just("")
				.delay(2, TimeUnit.SECONDS)
				.map(o -> UUID.randomUUID()
						.toString())
				.doOnNext(s -> Log.d("QWER", "doWork doOnNext"))
				.subscribeOn(schedulersProvider.io())
				.doOnCompleted(() -> Log.d("QWER", "doWork doOnCompleted"));
		subscribtionManager.subscribe(qwer);
	}

	@Override
	public Observable<String> doWorkWithResponse() {
		Log.d("QWER", "doWorkWithResponse start");
		Observable<String> qwer = Observable.just("")
				.delay(2, TimeUnit.SECONDS)
				.map(o -> UUID.randomUUID()
						.toString())
				.doOnNext(s -> Log.d("QWER", "doWorkWithResponse doOnNext"))
				.subscribeOn(schedulersProvider.io());
		return subscribtionManager.subscribeWithResult(qwer);
	}
}
