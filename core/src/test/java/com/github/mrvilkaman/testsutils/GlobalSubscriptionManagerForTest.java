package com.github.mrvilkaman.testsutils;


import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import rx.Observable;

public class GlobalSubscriptionManagerForTest implements GlobalSubscriptionManager {

	@Override
	public <T> void subscribe(Observable<T> observable) {
		observable.subscribe();
	}

	@Override
	public <T> Observable<T> subscribeWithResult(Observable<T> observable) {
		return observable;
	}

	@Override
	public <T> Observable<T> createCached(String key, Observable<T> observable) {
		return observable;
	}
}
