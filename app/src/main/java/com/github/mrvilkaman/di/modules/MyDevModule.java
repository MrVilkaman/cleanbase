package com.github.mrvilkaman.di.modules;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.dev.LeakCanaryProxy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class MyDevModule {

	@Provides
	@Singleton
	public LeakCanaryProxy provideLeakCanaryProxy(Context context) {
		return new MyLeakCanaryProxy(context);
	}

	private static class MyLeakCanaryProxy implements LeakCanaryProxy {

		private final Context context;
		private RefWatcher install;

		public MyLeakCanaryProxy(Context context) {
			this.context = context;
		}

		@Override
		public void init() {
			Application applicationContext = (Application) context.getApplicationContext();
			install = LeakCanary.install(applicationContext);
		}

		@Override
		public void watch(@NonNull Object object) {
			if (install != null) {
				install.watch(object);
			}
		}
	}
}
