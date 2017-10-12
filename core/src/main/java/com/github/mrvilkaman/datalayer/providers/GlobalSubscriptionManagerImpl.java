package com.github.mrvilkaman.datalayer.providers;

import android.util.Log;

import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.utils.bus.Bus;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

import static com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings.needSubscribeLogs;
import static com.github.mrvilkaman.presentationlayer.utils.DevUtils.getGlobalSubscriberStartStack;


@SuppressWarnings("Convert2Lambda")
public class GlobalSubscriptionManagerImpl implements GlobalSubscriptionManager {

	private final CompositeDisposable subscription = new CompositeDisposable();
	private final Bus bus;
	private HashMap<String, Object> objectObjectHashMap = new HashMap<>();

	public GlobalSubscriptionManagerImpl(Bus bus) {
		this.bus = bus;
	}


	@SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
	@Override
	public <T> Observable<T> createCached(final String key, Observable<T> source) {
		Observable<T> observable = (Observable<T>) objectObjectHashMap.get(key);
		if (observable == null) {
			Observable<T> cache = source.doOnTerminate(new Action() {
				@Override
				public void run() throws Exception {
					objectObjectHashMap.remove(key);
				}
			})
					.cache()
					.doOnDispose(new Action() {
						@Override
						public void run() throws Exception {
							objectObjectHashMap.remove(key);
						}
					});
			objectObjectHashMap.put(key, cache);
			observable = cache;
		}

		return observable;
	}

	@Override
	public <T> ObservableTransformer<T, T> subscribe() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
				final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
				subscription.add(upstream.subscribe(Functions.emptyConsumer(), new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						if (needSubscribeLogs()) {
							Log.e("GlobalSubscription", "Start by:" + string, throwable);
						}
						bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
					}
				}));
				return Observable.empty();
			}
		};
	}

	@Override
	public <T> ObservableTransformer<T, T> subscribeWithResult() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(@NonNull final Observable<T> upstream) {
				final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
				return Observable.create(new ObservableOnSubscribe<T>() {
					@Override
					public void subscribe(final @NonNull ObservableEmitter<T> subscriber) throws Exception {
						subscription.add(upstream.subscribe(new Consumer<T>() {
							@Override
							public void accept(T t) throws Exception {
								if (!subscriber.isDisposed())
									subscriber.onNext(t);
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {
								if (subscriber.isDisposed()) {
									if (needSubscribeLogs()) {
										Log.e("GlobalSubscription", "Start by:" + string, throwable);
									}
									bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
								} else {
									subscriber.onError(throwable);
								}
							}
						}, new Action() {
							@Override
							public void run() throws Exception {
								if (!subscriber.isDisposed())
									subscriber.onComplete();
							}
						}));
					}
				});
			}
		};
	}

	@Override
	public <T> Single<T> createCachedSingle(final String key, Single<T> source) {
		Single<T> observable = (Single<T>) objectObjectHashMap.get(key);
		if (observable == null) {
			Single<T> cache = source.doAfterTerminate(new Action() {
				@Override
				public void run() throws Exception {
					objectObjectHashMap.remove(key);
				}
			})
					.cache()
					.doOnDispose(new Action() {
						@Override
						public void run() throws Exception {
							objectObjectHashMap.remove(key);
						}
					});
			objectObjectHashMap.put(key, cache);
			observable = cache;
		}

		return observable;
	}


	@Override
	public <T> SingleTransformer<T, T> subscribeSingle() {
		return new SingleTransformer<T, T>() {
			@Override
			public SingleSource<T> apply(@NonNull Single<T> upstream) {
				final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
				subscription.add(upstream.subscribe(Functions.emptyConsumer(), new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						if (needSubscribeLogs()) {
							Log.e("GlobalSubscription", "Start by:" + string, throwable);
						}
						bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
					}
				}));
				return upstream;
			}
		};
	}

	@Override
	public <T> SingleTransformer<T, T> subscribeWithResultSingle() {
		return new SingleTransformer<T, T>() {
			@Override
			public SingleSource<T> apply(final @NonNull Single<T> upstream) {
				return Single.create(new SingleOnSubscribe<T>() {
					@Override
					public void subscribe(final @NonNull SingleEmitter<T> subscriber) throws Exception {
						final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
						subscription.add(upstream.subscribe(
								new Consumer<T>() {
									@Override
									public void accept(T t) throws Exception {
										if (!subscriber.isDisposed())
											subscriber.onSuccess(t);
									}
								},
								new Consumer<Throwable>() {
									@Override
									public void accept(Throwable throwable) throws Exception {
										if (subscriber.isDisposed()) {
											if (needSubscribeLogs()) {
												Log.e("GlobalSubscription", "Start by:" + string, throwable);
											}
											bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
										} else {
											subscriber.onError(throwable);
										}
									}
								}
						));
					}
				});
			}
		};
	}

	@Override
	public Completable createCachedCompletable(final String key, Completable source) {
		Completable observable = (Completable) objectObjectHashMap.get(key);
		if (observable == null) {
			Completable cache = source.doOnTerminate(new Action() {
				@Override
				public void run() throws Exception {
					objectObjectHashMap.remove(key);
				}
			})
					.cache()
					.doOnDispose(new Action() {
						@Override
						public void run() throws Exception {
							objectObjectHashMap.remove(key);
						}
					});
			objectObjectHashMap.put(key, cache);
			observable = cache;
		}

		return observable;
	}

	@Override
	public CompletableTransformer subscribeCompletable() {
		return new CompletableTransformer() {
			@Override
			public CompletableSource apply(@NonNull Completable upstream) {
				final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
				subscription.add(upstream.subscribe(Functions.EMPTY_ACTION, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						if (needSubscribeLogs()) {
							Log.e("GlobalSubscription", "Start by:" + string, throwable);
						}
						bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
					}
				}));
				return Completable.complete();
			}
		};
	}

	@Override
	public CompletableTransformer subscribeWithResultCompletable() {
		return new CompletableTransformer() {
			@Override
			public CompletableSource apply(final @NonNull Completable upstream) {
				return Completable.create(new CompletableOnSubscribe() {
					@Override
					public void subscribe(final @NonNull CompletableEmitter subscriber) throws
							Exception {
						final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
						subscription.add(upstream.subscribe(
								new Action() {
									@Override
									public void run() throws Exception {
										if (!subscriber.isDisposed())
											subscriber.onComplete();
									}
								}
								, new Consumer<Throwable>() {
									@Override
									public void accept(Throwable throwable) throws Exception {
										if (subscriber.isDisposed()) {
											if (needSubscribeLogs()) {
												Log.e("GlobalSubscription", "Start by:" + string, throwable);
											}
											bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
										} else {
											subscriber.onError(throwable);
										}
									}
								}
						));
					}
				});
			}
		};
	}
}
