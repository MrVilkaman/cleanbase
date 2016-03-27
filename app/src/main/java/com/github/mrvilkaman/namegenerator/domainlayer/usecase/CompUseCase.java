package com.github.mrvilkaman.namegenerator.domainlayer.usecase;

import com.github.mrvilkaman.namegenerator.domainlayer.providers.SchedulersProvider;

/**
 * Created by Zahar on 19.03.16.
 */
public abstract class CompUseCase<T> extends UseCase<T> {

	public CompUseCase(SchedulersProvider provider){
		super(provider.computation(), provider.mainThread());
	}
}
