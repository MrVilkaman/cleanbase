package com.github.mrvilkaman.domainlayer.providers;


import rx.Observable;
import rx.annotations.Beta;
import rx.annotations.Experimental;

@Experimental
public interface GlobalSubscriptionManager {

	@Experimental
	<T> void subscribe(Observable<T> observable);

	@Experimental
	<T> Observable<T> subscribeWithResult(Observable<T> observable);

	@Beta
	@Experimental
	<T> Observable<T> createCached(String key, Observable<T> observable);
}
