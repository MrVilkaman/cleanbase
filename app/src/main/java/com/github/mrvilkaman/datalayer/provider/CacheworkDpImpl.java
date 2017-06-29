package com.github.mrvilkaman.datalayer.provider;


import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class CacheworkDpImpl implements CacheworkDp {

	private BehaviorSubject<String> subject = BehaviorSubject.create();
	private BehaviorSubject<Boolean> subjectProgress = BehaviorSubject.create(false);
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
	public Observable<Boolean> observeStringProgress() {
		return subjectProgress.asObservable();
	}

	@Override
	public boolean getCurrentProgress() {
		return subjectProgress.getValue();
	}

	@Override
	public void refreshString() {
		Observable.just(UUID.randomUUID().toString())
				.delay(2, TimeUnit.SECONDS)
				.doOnNext(subject::onNext)
				.doOnSubscribe(() -> subjectProgress.onNext(true))
				.doOnTerminate(() -> subjectProgress.onNext(false))
				.compose(manager.subscribe());
	}
}
