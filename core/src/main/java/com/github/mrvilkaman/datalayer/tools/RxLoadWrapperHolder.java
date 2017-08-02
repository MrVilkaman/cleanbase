package com.github.mrvilkaman.datalayer.tools;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxLoadWrapperHolder {


	private BehaviorSubject<Boolean> progress;
	private PublishSubject<Throwable> error = PublishSubject.create();

	private RxLoadWrapperHolder(Boolean b) {
		progress = b != null ? BehaviorSubject.createDefault(b) : BehaviorSubject.create();
	}

	public static RxLoadWrapperHolder create() {
		return new RxLoadWrapperHolder(false);
	}

	public static RxLoadWrapperHolder createSilent() {
		return new RxLoadWrapperHolder(null);
	}

	public <T> ObservableTransformer<T, T> bindToLoad() {
		return this::bind;
	}

	public <T> ObservableTransformer<T, T> bindToLoadSilent() {
		return observable -> bind(observable).onErrorResumeNext(Observable.empty());
	}

	public <D> Observable<DataErrorWrapper<D>> modifyStream(Observable<D> observable) {
		return observable.map(d -> new DataErrorWrapper<>(d, progress.getValue()))
				.mergeWith(error.map(
						throwable -> new DataErrorWrapper<>(throwable, progress.getValue())))
				.mergeWith(progress.map(DataErrorWrapper::new));
	}

	public <T> ObservableTransformer<T, DataErrorWrapper<T>> modifyStream() {
		return this::modifyStream;
	}

	public <D> Observable<DataErrorWrapper<D>> modifySingle(Observable<D> observable) {
		return observable.map(d -> new DataErrorWrapper<>(d, false))
				.startWith(new DataErrorWrapper<>(true))
				.onErrorReturn(throwable -> new DataErrorWrapper(throwable, false));
	}

	public <T> ObservableTransformer<T, DataErrorWrapper<T>> modifySingle() {
		return this::modifySingle;
	}

	@NonNull
	private <T> Observable<T> bind(Observable<T> observable) {
		return observable.doOnSubscribe(disposable -> progress.onNext(true))
				.doOnTerminate(() -> progress.onNext(false))
				.doOnError(error::onNext);
	}
}
