package com.github.mrvilkaman.domainlayer.interactor;

import android.util.Log;

import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class LongpullingInteractorImpl implements LongpullingInteractor {

	private final SchedulersProvider schedulersProvider;
	private final CompositeSubscription subscription;

	public LongpullingInteractorImpl(SchedulersProvider schedulersProvider) {
		this.schedulersProvider = schedulersProvider;

		subscription = new CompositeSubscription();
	}

	@Override
	public void doWorkWithError() {
		Log.d("QWER","doWorkWithError start");
		Observable<String> qwer = Observable.error(new NotFoundException())
				.delay(3, TimeUnit.SECONDS)
				.map(o -> UUID.randomUUID()
						.toString())
				.doOnNext(s -> Log.d("QWER", "doWorkWithError doOnNext"))
				.subscribeOn(schedulersProvider.io())
				.doOnError(throwable -> Log.d("QWER", "doWorkWithError doOnError"));
		subscription.add(qwer.subscribe());
	}

	@Override
	public void doWork() {
		Log.d("QWER","doWork start");
		Observable<String> qwer = Observable.just("")
				.delay(3, TimeUnit.SECONDS)
				.map(o -> UUID.randomUUID()
						.toString())
				.doOnNext(s -> Log.d("QWER", "doWork doOnNext"))
				.subscribeOn(schedulersProvider.io())
				.doOnCompleted(() -> Log.d("QWER", "doWork doOnCompleted"));
		subscription.add(qwer.subscribe());
	}

	@Override
	public Observable<String> doWorkWithResponse() {
		Log.d("QWER","doWorkWithResponse start");
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> qwer = Observable.just("")
				.delay(3, TimeUnit.SECONDS)
				.map(o -> UUID.randomUUID()
						.toString())
				.doOnNext(s -> Log.d("QWER", "doWorkWithResponse doOnNext"))
				.subscribeOn(schedulersProvider.io());

		subscription.add(qwer.subscribe(subject));

		return subject.asObservable();
	}
}
