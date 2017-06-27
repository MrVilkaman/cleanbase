package com.github.mrvilkaman.presentationlayer.resolution;


import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import java.util.concurrent.TimeUnit;

import rx.annotations.Experimental;
import rx.subjects.PublishSubject;

@Experimental
public class ThrowableResolverRxImpl implements ThrowableResolver {


	private final SchedulersProvider provider;
	private ThrowableResolver throwableResolver;

	private PublishSubject<Throwable> subject = PublishSubject.create();

	public ThrowableResolverRxImpl(ThrowableResolver throwableResolver,
								   SchedulersProvider provider) {
		this.throwableResolver = throwableResolver;
		this.provider = provider;
	}

	@Override
	public void handleError(Throwable throwable) {
		subject.onNext(throwable);
	}

	public void init() {
		subject.groupBy(Throwable::getClass)
				.flatMap(obs -> obs.throttleFirst(500, TimeUnit.MILLISECONDS,
						provider.computation()))

				//		subject
				.observeOn(provider.mainThread())
				.subscribe(throwableResolver::handleError);
	}
}
