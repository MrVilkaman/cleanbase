package com.github.mrvilkaman.datalayer.provider;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.datalayer.tools.ProgressStateHolder;
import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.jakewharton.rxrelay.BehaviorRelay;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

public class CacheworkDpImpl implements CacheworkDp {

	private BehaviorRelay<String> subject = BehaviorRelay.create();
	private GlobalSubscriptionManager manager;
	private SchedulersProvider provider;

	private ProgressStateHolder stringProgress = ProgressStateHolder.create();

	@Inject
	public CacheworkDpImpl(GlobalSubscriptionManager manager, SchedulersProvider provider) {
		this.manager = manager;
		this.provider = provider;
	}

	@Override
	public Observable<DataErrorWrapper<String>> observeString() {
		return stringProgress.handle(subject.asObservable());

	}

	@Override
	public void refreshString() {
		getJust().delay(2, TimeUnit.SECONDS, provider.computation())
				.doOnNext((v) -> subject.call(v))
				// Знаю что так делать нельзя, то это для примера)
				.compose(stringProgress.bindProgress())
				.onErrorResumeNext(throwable -> Observable.empty())
				.compose(manager.subscribe());
	}

	@NonNull
	public Observable<String> getJust() {return Observable.just(getUuid());}

	public String getUuid() {
		return UUID.randomUUID().toString();
	}
}
