package com.github.mrvilkaman.namegenerator.domainlayer.usecase.core;

import com.github.mrvilkaman.namegenerator.domainlayer.providers.SchedulersProvider;

/**
 * Created by Zahar on 19.03.16.
 */
public abstract class LoadUseCase<T> extends UseCase<T> {

	public LoadUseCase(SchedulersProvider provider) {
		super(provider.io(), provider.mainThread());
	}
}
