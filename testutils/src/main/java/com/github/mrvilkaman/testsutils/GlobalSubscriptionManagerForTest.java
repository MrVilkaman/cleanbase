package com.github.mrvilkaman.testsutils;


import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;


public class GlobalSubscriptionManagerForTest implements GlobalSubscriptionManager {


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
	public <T> SingleTransformer<T, T> subscribeSingle() {
		return new SingleTransformer<T, T>() {
			@Override
			public SingleSource<T> apply(@NonNull Single<T> upstream) {
				upstream.subscribe();
				return upstream;
			}
		};
	}

	@Override
	public <T> Single<T> createCachedSingle(String key, Single<T> observable) {
		return observable;
	}

	@Override
	public <T> SingleTransformer<T, T> subscribeWithResultSingle() {
		return new SingleTransformer<T, T>() {
			@Override
			public SingleSource<T> apply(@NonNull Single<T> upstream) {
				return upstream;
			}
		};
	}

	@Override
	public Completable createCachedCompletable(String key, Completable observable) {
		return observable;
	}

	@Override
	public CompletableTransformer subscribeCompletable() {
		return new CompletableTransformer() {
			@Override
			public CompletableSource apply(@NonNull Completable upstream) {
				return upstream;
			}
		};
	}

	@Override
	public CompletableTransformer subscribeWithResultCompletable() {
		return new CompletableTransformer() {
			@Override
			public CompletableSource apply(@NonNull Completable upstream) {
				return upstream;
			}
		};
	}
}
