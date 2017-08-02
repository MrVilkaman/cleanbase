package com.github.mrvilkaman.datalayer.provider;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.datalayer.tools.RxLoadWrapperHolder;
import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


public class CacheworkDpImpl implements CacheworkDp {

	private BehaviorSubject<String> subject = BehaviorSubject.create();
	private PublishSubject<StringBody> subjectBody = PublishSubject.create();

	private GlobalSubscriptionManager manager;
	private SchedulersProvider provider;

	private RxLoadWrapperHolder stringProgress = RxLoadWrapperHolder.create();
	private RxLoadWrapperHolder stringBodyProgress = RxLoadWrapperHolder.createSilent();

	@Inject
	public CacheworkDpImpl(GlobalSubscriptionManager manager, SchedulersProvider provider) {
		this.manager = manager;
		this.provider = provider;
	}

	@Override
	public Observable<DataErrorWrapper<String>> observeString() {
		return stringProgress.modifyStream(subject);
	}

	@Override
	public Observable<DataErrorWrapper<StringBody>> observeStringModel() {
		return stringBodyProgress.modifyStream(subjectBody);
	}

	@Override
	public void refreshString() {
		getJust().delay(2, TimeUnit.SECONDS, provider.computation())
				.doOnNext(stringBody -> subject.onNext(stringBody))
				.compose(stringProgress.bindToLoadSilent())
				.compose(manager.subscribe());
	}

	@Override
	public void refreshStringWrapper() {
		getJust().delay(2, TimeUnit.SECONDS, provider.computation())
				.map(StringBody::new)
				.doOnNext(stringBody -> subjectBody.onNext(stringBody))
				.compose(stringBodyProgress.bindToLoadSilent())
				.compose(manager.subscribe());
	}

	@NonNull
	public Observable<String> getJust() {return Observable.just(getUuid());}

	public String getUuid() {
		return UUID.randomUUID().toString();
	}
}
