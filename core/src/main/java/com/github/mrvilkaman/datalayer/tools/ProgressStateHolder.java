package com.github.mrvilkaman.datalayer.tools;


import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class ProgressStateHolder {


	private BehaviorSubject<Boolean> progress = BehaviorSubject.create(false);
	private PublishSubject<Throwable> error = PublishSubject.create();

	private ProgressStateHolder() {
	}

	public static ProgressStateHolder create() {
		return new ProgressStateHolder();
	}

	public <T> Observable.Transformer<T, T> bindProgress() {
		return observable -> observable.doOnSubscribe(() -> progress.onNext(true))
				.doOnTerminate(() -> progress.onNext(false))
				.doOnError(error::onNext);
	}

	public <D> Observable<DataErrorWrapper<D>> handle(Observable<D> observable) {
		return observable.map(d -> new DataErrorWrapper<>(d, progress.getValue()))
				.mergeWith(error.map(
						throwable -> new DataErrorWrapper<>(throwable, progress.getValue())))
				.mergeWith(progress.map((progress1) -> new DataErrorWrapper<D>(progress1)));
	}
}
