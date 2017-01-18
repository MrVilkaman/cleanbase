package com.github.mrvilkaman.datalayer.providers;

import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;

import net.jokubasdargis.rxbus.Bus;

import rx.Observable;
import rx.functions.Actions;
import rx.subscriptions.CompositeSubscription;


public class GlobalSubscriptionManagerImpl implements GlobalSubscriptionManager {

	private final CompositeSubscription subscription = new CompositeSubscription();
	private final Bus bus;

	public GlobalSubscriptionManagerImpl(Bus bus) {
		this.bus = bus;
	}

	@Override
	public <T> void subscribe(Observable<T> qwer) {
		subscription.add(qwer.subscribe(t -> Actions.empty(),
				throwable -> bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable)));
	}

	@Override
	public <T> Observable<T> subscribeWithResult(Observable<T> qwer) {
		return Observable.create(subscriber -> {
			subscription.add(qwer.subscribe((t) -> {
				if (!subscriber.isUnsubscribed())
					subscriber.onNext(t);
			}, throwable -> {
				if (subscriber.isUnsubscribed()) {
					bus.publish(GlobalBusQuery.GLOBAL_ERRORS, throwable);
				} else {
					subscriber.onError(throwable);
				}
			}, () -> {
				if (!subscriber.isUnsubscribed())
					subscriber.onCompleted();
			}));
		});
	}
}
