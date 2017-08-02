package com.github.mrvilkaman.di.modules;

import com.github.mrvilkaman.datalayer.providers.GlobalSubscriptionManagerImpl;
import com.github.mrvilkaman.datalayer.providers.MainSchedulersProvider;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.utils.bus.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
@Singleton
public class CoreProvidersModule {

	@Singleton
	@Provides
	SchedulersProvider provideSchedulersProvider() {
		return new MainSchedulersProvider();
	}

	@Provides
	@Singleton
	public GlobalSubscriptionManager getGlobalSubscriptionManager(Bus bus) {
		return new GlobalSubscriptionManagerImpl(bus);
	}
}
