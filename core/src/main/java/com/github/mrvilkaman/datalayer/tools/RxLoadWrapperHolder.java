package com.github.mrvilkaman.datalayer.tools;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

import rx.Observable;
import rx.annotations.Experimental;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

@Experimental
public class RxLoadWrapperHolder {


	private BehaviorSubject<Boolean> progress;
	private PublishSubject<Throwable> error = PublishSubject.create();

	private RxLoadWrapperHolder(Boolean b) {
		progress = b != null ? BehaviorSubject.create(b) : BehaviorSubject.create();
	}

	@Experimental
	public static RxLoadWrapperHolder create() {
		return new RxLoadWrapperHolder(false);
	}

	@Experimental
	public static RxLoadWrapperHolder createSilent() {
		return new RxLoadWrapperHolder(null);
	}

	@Experimental
	public <T> Observable.Transformer<T, T> bindToLoad() {
		return this::bind;
	}

	@Experimental
	public <T> Observable.Transformer<T, T> bindToLoadSilent() {
		return observable -> bind(observable).onErrorResumeNext(throwable -> Observable.empty());
	}

	@Experimental
	public <D> Observable<DataErrorWrapper<D>> modifyStream(Observable<D> observable) {
		return observable.map(d -> new DataErrorWrapper<>(d, progress.getValue()))
				.mergeWith(error.map(
						throwable -> new DataErrorWrapper<>(throwable, progress.getValue())))
				.mergeWith(progress.map(DataErrorWrapper::new));
	}

	@NonNull
	private <T> Observable<T> bind(Observable<T> observable) {
		return observable.doOnSubscribe(() -> progress.onNext(true))
				.doOnTerminate(() -> progress.onNext(false))
				.doOnError(error::onNext);
	}
}
