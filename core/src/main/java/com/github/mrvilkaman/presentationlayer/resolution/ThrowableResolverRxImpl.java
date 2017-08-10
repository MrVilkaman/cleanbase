package com.github.mrvilkaman.presentationlayer.resolution;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.datalayer.providers.GlobalBusQuery;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.utils.bus.Bus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.subjects.PublishSubject;

public class ThrowableResolverRxImpl implements ThrowableResolver {


	private final SchedulersProvider provider;
	private ThrowableResolver throwableResolver;
	private Bus bus;

	private PublishSubject<Throwable> subject = PublishSubject.create();

	public ThrowableResolverRxImpl(@NonNull ThrowableResolver throwableResolver, @NonNull Bus bus,
	                               @NonNull SchedulersProvider provider) {
		this.throwableResolver = throwableResolver;
		this.provider = provider;
		this.bus = bus;
	}

	@Override
	public void handleError(Throwable throwable) {
		subject.onNext(throwable);
	}


	public void init() {

		subject.mergeWith(bus.queue(GlobalBusQuery.GLOBAL_ERRORS))
				.groupBy(Throwable::getClass)
				.flatMap(new Function<GroupedObservable<? extends Class<? extends Throwable>, Throwable>, Observable<Throwable>>() {
					@Override
					public Observable<Throwable> apply(GroupedObservable<? extends Class<? extends Throwable>, Throwable> obs) throws Exception {
						return obs.throttleFirst(500, TimeUnit.MILLISECONDS,
								provider.computation());
					}
				})
				.observeOn(provider.mainThread())
				.subscribe(new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						throwableResolver.handleError(throwable);
					}
				});
	}
}
