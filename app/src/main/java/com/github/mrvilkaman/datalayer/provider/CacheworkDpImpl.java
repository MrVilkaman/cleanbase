package com.github.mrvilkaman.datalayer.provider;


import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class CacheworkDpImpl implements CacheworkDp {

	private BehaviorSubject<String> subject = BehaviorSubject.create();
	private GlobalSubscriptionManager manager;

	@Inject
	public CacheworkDpImpl(GlobalSubscriptionManager manager) {
		this.manager = manager;
	}

	@Override
	public Observable<String> observeString() {
		return subject.asObservable();
	}

	@Override
	public void refreshString() {
		Observable.just(UUID.randomUUID().toString())
				.delay(2, TimeUnit.SECONDS)
				.doOnNext(subject::onNext)
				.compose(manager.subscribe());
	}
}
