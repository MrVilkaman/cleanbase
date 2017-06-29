package com.github.mrvilkaman.datalayer.provider;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class CacheworkDpImpl implements CacheworkDp {

	private BehaviorSubject<String> subject = BehaviorSubject.create();
	private BehaviorSubject<Boolean> subjectProgress = BehaviorSubject.create(false);
	private GlobalSubscriptionManager manager;
	private SchedulersProvider provider;

	@Inject
	public CacheworkDpImpl(GlobalSubscriptionManager manager, SchedulersProvider provider) {
		this.manager = manager;
		this.provider = provider;
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
		getJust().delay(2, TimeUnit.SECONDS, provider.computation())
				.doOnNext(subject::onNext)
				.doOnSubscribe(() -> subjectProgress.onNext(true))
				.doOnTerminate(() -> subjectProgress.onNext(false))
				.compose(manager.subscribe());
	}

	@NonNull
	public Observable<String> getJust() {return Observable.just(getUuid());}

	public String getUuid() {
		return UUID.randomUUID().toString();
	}
}
