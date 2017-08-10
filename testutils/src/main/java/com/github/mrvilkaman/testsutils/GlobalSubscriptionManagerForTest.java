package com.github.mrvilkaman.testsutils;


import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;


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

	@Override
	public <T> ObservableTransformer<T, T> subscribe() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> observable) {
				observable.subscribe();
				return Observable.empty();
			}
		};
	}

	@Override
	public <T> ObservableTransformer<T, T> subscribeWithResult() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> observable) {
				return observable;
			}
		};
	}

	@Override
	public <T> ObservableTransformer<T, T> createCached(String key) {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> observable) {
				return observable;
			}
		};
	}
}
