package com.github.mrvilkaman.datalayer.providers;

import android.util.Log;

import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import net.jokubasdargis.rxbus.Bus;

import java.util.HashMap;

import rx.Observable;
import rx.functions.Actions;
import rx.subscriptions.CompositeSubscription;

import static com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings.needSubscribeLogs;
import static com.github.mrvilkaman.presentationlayer.utils.DevUtils.getGlobalSubscriberStartStack;


public class GlobalSubscriptionManagerImpl implements GlobalSubscriptionManager {

	private final CompositeSubscription subscription = new CompositeSubscription();
	private final Bus bus;
	private HashMap<String, Observable> objectObjectHashMap = new HashMap<>();

	public GlobalSubscriptionManagerImpl(Bus bus) {
		this.bus = bus;
	}

	@Override
	public <T> void subscribe(Observable<T> qwer) {
		String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
		subscription.add(qwer.subscribe(t -> Actions.empty(), throwable -> {
			if (needSubscribeLogs()) {
				Log.e("GlobalSubscription", "Start by:" + string, throwable);
			}
			bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
		}));
	}

	@Override
	public <T> Observable<T> subscribeWithResult(Observable<T> qwer) {
		String string = needSubscribeLogs() ? getGlobalSubscriberStartStack() : "";
		return Observable.unsafeCreate(subscriber -> subscription.add(qwer.subscribe((t) -> {
			if (!subscriber.isUnsubscribed())
				subscriber.onNext(t);
		}, throwable -> {
			if (subscriber.isUnsubscribed()) {
				if (needSubscribeLogs()) {
					Log.e("GlobalSubscription", "Start by:" + string, throwable);
				}
				bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
			} else {
				subscriber.onError(throwable);
			}
		}, () -> {
			if (!subscriber.isUnsubscribed())
				subscriber.onCompleted();
		})));
	}

	@SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
	@Override
	public <T> Observable<T> createCached(String key, Observable<T> source) {
		Observable<T> observable = objectObjectHashMap.get(key);
		if (observable == null) {
			Observable<T> cache = source.doOnTerminate(() -> objectObjectHashMap.remove(key))
					.cache()
					.doOnUnsubscribe(() -> objectObjectHashMap.remove(key));
			objectObjectHashMap.put(key, cache);
			observable = cache;
		}

		return observable;
	}

	@Override
	public <T> Observable.Transformer<T, T> subscribe() {
		return observable -> {
			subscribe(observable);
			return Observable.empty();
		};
	}

	@Override
	public <T> Observable.Transformer<T, T> subscribeWithResult() {
		return this::subscribeWithResult;
	}

	@Override
	public <T> Observable.Transformer<T, T> createCached(String key) {
		return observable -> createCached(key, observable);
	}
}
