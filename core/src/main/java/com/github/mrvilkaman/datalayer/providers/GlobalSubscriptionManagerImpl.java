package com.github.mrvilkaman.datalayer.providers;

import android.util.Log;

import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.utils.bus.Bus;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

import static com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings.needSubscribeLogs;
import static com.github.mrvilkaman.presentationlayer.utils.DevUtils.getGlobalSubscriberStartStack;


public class GlobalSubscriptionManagerImpl implements GlobalSubscriptionManager {

	private final CompositeDisposable subscription = new CompositeDisposable();
	private final Bus bus;
	private HashMap<String, Observable> objectObjectHashMap = new HashMap<>();

	public GlobalSubscriptionManagerImpl(Bus bus) {
		this.bus = bus;
	}

	@Override
	public <T> void subscribe(Observable<T> qwer) {
		final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
		subscription.add(qwer.subscribe(Functions.emptyConsumer(), new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				if (needSubscribeLogs()) {
					Log.e("GlobalSubscription", "Start by:" + string, throwable);
				}
				bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
			}
		}));
	}

	@Override
	public <T> Observable<T> subscribeWithResult(final Observable<T> qwer) {
		final String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
		return Observable.create(new ObservableOnSubscribe<T>() {
			@Override
			public void subscribe(final @NonNull ObservableEmitter<T> subscriber) throws Exception {
				subscription.add(qwer.subscribe(new Consumer<T>() {
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

	@SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
	@Override
	public <T> Observable<T> createCached(final String key, Observable<T> source) {
		Observable<T> observable = objectObjectHashMap.get(key);
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
				subscribe(upstream);
				return Observable.empty();
			}
		};
	}

	@Override
	public <T> ObservableTransformer<T, T> subscribeWithResult() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
				return subscribeWithResult(upstream);
			}
		};
	}

	@Override
	public <T> ObservableTransformer<T, T> createCached(final String key) {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
				return createCached(key, upstream);
			}
		};
	}
}
