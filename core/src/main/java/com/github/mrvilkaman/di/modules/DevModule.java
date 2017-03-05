package com.github.mrvilkaman.di.modules;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.dev.LeakCanaryProxy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class DevModule {

	@Provides
	@Singleton
	@Nullable
	public static LeakCanaryProxy provideLeakCanaryProxy() {
		return null;
	}
}
