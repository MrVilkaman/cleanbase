package com.github.mrvilkaman.domainlayer.providers;


import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public interface GlobalSubscriptionManager {
	//// TODO: 02.08.17 Added maybe\Compliteble\Flowable\Single

	<T> void subscribe(Observable<T> observable);

	<T> Observable<T> subscribeWithResult(Observable<T> observable);

	<T> Observable<T> createCached(String key, Observable<T> observable);

	<T> ObservableTransformer<T, T> subscribe();

	<T> ObservableTransformer<T, T> subscribeWithResult();

	<T> ObservableTransformer<T, T> createCached(String key);
}
