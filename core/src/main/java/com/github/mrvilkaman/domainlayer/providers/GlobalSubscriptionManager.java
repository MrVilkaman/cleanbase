package com.github.mrvilkaman.domainlayer.providers;


import rx.Observable;

public interface GlobalSubscriptionManager {

	<T> void subscribe(Observable<T> observable);

	<T> Observable<T> subscribeWithResult(Observable<T> observable);

	<T> Observable<T> createCached(String key, Observable<T> observable);

	<T> Observable.Transformer<T,T> subscribe();

	<T> Observable.Transformer<T,T> subscribeWithResult();

	<T> Observable.Transformer<T,T>  createCached(String key);
}
