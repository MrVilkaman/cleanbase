package com.github.mrvilkaman.domainlayer.providers;


import rx.Observable;
import rx.annotations.Experimental;

@Experimental
public interface GlobalSubscriptionManager {

	@Experimental
	<T> void subscribe(Observable<T> qwer);

	@Experimental
	<T> Observable<T> subscribeWithResult(Observable<T> qwer);
}
