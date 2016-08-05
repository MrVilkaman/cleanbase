package ru.fixapp.fooproject.domainlayer.usecase.core;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;

/**
 * Created by Zahar on 19.03.16.
 */
public abstract class LoadUseCase<T> extends UseCase<T> {

	public LoadUseCase(SchedulersProvider provider) {
		super(provider.io(), provider.mainThread());
	}
}
