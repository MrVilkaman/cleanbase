package com.github.mrvilkaman.di.modules;


import com.github.mrvilkaman.dev.LeakCanaryProxy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class DevModule {

	@Provides
	@Singleton
	public LeakCanaryProxy provideLeakCanaryProxy() {
		return null;
	}
}
