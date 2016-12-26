package com.github.mrvilkaman.di.modules;

import com.github.mrvilkaman.datalayer.providers.MainSchedulersProvider;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
@Singleton
public class ProvidersModule {

	@Singleton
	@Provides
	SchedulersProvider provideSchedulersProvider() {
		return new MainSchedulersProvider();
	}

}
