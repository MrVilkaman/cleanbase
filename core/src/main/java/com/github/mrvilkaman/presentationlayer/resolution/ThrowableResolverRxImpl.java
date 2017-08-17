package com.github.mrvilkaman.presentationlayer.resolution;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.datalayer.providers.GlobalBusQuery;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import net.jokubasdargis.rxbus.Bus;

import java.util.concurrent.TimeUnit;

import rx.annotations.Experimental;
import rx.functions.Actions;
import rx.subjects.PublishSubject;

@Experimental
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
				.flatMap(obs -> obs.throttleFirst(500, TimeUnit.MILLISECONDS,
						provider.computation()))
				//		subject
				.observeOn(provider.mainThread())
				.subscribe(throwableResolver::handleError, Actions.empty());
	}
}
