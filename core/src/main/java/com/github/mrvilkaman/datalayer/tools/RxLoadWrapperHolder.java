package com.github.mrvilkaman.datalayer.tools;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxLoadWrapperHolder {


	private BehaviorSubject<Boolean> progress;
	private PublishSubject<Throwable> error = PublishSubject.create();

	private RxLoadWrapperHolder(Boolean b) {
		progress = b != null ? BehaviorSubject.createDefault(b) : BehaviorSubject.<Boolean>create();
	}

	public static RxLoadWrapperHolder create() {
		return new RxLoadWrapperHolder(false);
	}

	public static RxLoadWrapperHolder createSilent() {
		return new RxLoadWrapperHolder(null);
	}

	public <T> ObservableTransformer<T, T> bindToLoad() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> observable) {
				return RxLoadWrapperHolder.this.bind(observable);
			}
		};
	}

	public <T> ObservableTransformer<T, T> bindToLoadSilent() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> observable) {
				return RxLoadWrapperHolder.this.bind(observable)
						.onErrorResumeNext(Observable.<T>empty());
			}
		};
	}

	public <D> Observable<DataErrorWrapper<D>> modifyStream(Observable<D> observable) {
		return observable.map(new Function<D, DataErrorWrapper<D>>() {
			@Override
			public DataErrorWrapper<D> apply(D d) throws Exception {
				return new DataErrorWrapper<>(d, progress.getValue());
			}
		})
				.mergeWith(error.map(
						new Function<Throwable, DataErrorWrapper<D>>() {
							@Override
							public DataErrorWrapper<D> apply(Throwable throwable) throws Exception {
								return new DataErrorWrapper<>(throwable, progress.getValue());
							}
						}))
				.mergeWith(progress.map(new Function<Boolean, DataErrorWrapper<D>>() {
					@Override
					public DataErrorWrapper<D> apply(Boolean progress1) throws Exception {
						return new DataErrorWrapper<>(progress1);
					}
				}));
	}

	public <T> ObservableTransformer<T, DataErrorWrapper<T>> modifyStream() {
		return new ObservableTransformer<T, DataErrorWrapper<T>>() {
			@Override
			public ObservableSource<DataErrorWrapper<T>> apply(Observable<T> observable) {
				return RxLoadWrapperHolder.this.modifyStream(observable);
			}
		};
	}

	public <D> Observable<DataErrorWrapper<D>> modifySingle(Observable<D> observable) {
		return observable.map(new Function<D, DataErrorWrapper<D>>() {
			@Override
			public DataErrorWrapper<D> apply(D d) throws Exception {
				return new DataErrorWrapper<>(d, false);
			}
		})
				.startWith(new DataErrorWrapper<D>(true))
				.onErrorReturn(new Function<Throwable, DataErrorWrapper<D>>() {
					@Override
					public DataErrorWrapper<D> apply(Throwable throwable) throws Exception {
						return new DataErrorWrapper(throwable, false);
					}
				});
	}

	public <T> ObservableTransformer<T, DataErrorWrapper<T>> modifySingle() {
		return new ObservableTransformer<T, DataErrorWrapper<T>>() {
			@Override
			public ObservableSource<DataErrorWrapper<T>> apply(Observable<T> observable) {
				return RxLoadWrapperHolder.this.modifySingle(observable);
			}
		};
	}

	@NonNull
	private <T> Observable<T> bind(Observable<T> observable) {
		return observable.doOnSubscribe(new Consumer<Disposable>() {
			@Override
			public void accept(Disposable disposable) throws Exception {
				progress.onNext(true);
			}
		})
				.doOnTerminate(new Action() {
					@Override
					public void run() throws Exception {
						progress.onNext(false);
					}
				})
				.doOnError(new Consumer<Throwable>() {
					@Override
					public void accept(Throwable t) throws Exception {
						error.onNext(t);
					}
				});
	}
}
