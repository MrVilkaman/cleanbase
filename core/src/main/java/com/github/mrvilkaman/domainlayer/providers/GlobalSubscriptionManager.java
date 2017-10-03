package com.github.mrvilkaman.domainlayer.providers;


import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public interface GlobalSubscriptionManager {

	//	Observable
	<T> Observable<T> createCached(String key, Observable<T> observable);

	<T> ObservableTransformer<T, T> subscribe();

	<T> ObservableTransformer<T, T> subscribeWithResult();

	//Single
	<T> Single<T> createCachedSingle(String key, Single<T> observable);

	<T> SingleTransformer<T, T> subscribeSingle();

	<T> SingleTransformer<T, T> subscribeWithResultSingle();

	//Completable
	Completable createCachedCompletable(String key, Completable observable);

	CompletableTransformer subscribeCompletable();

	CompletableTransformer subscribeWithResultCompletable();
}
