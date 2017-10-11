package com.github.mrvilkaman.ui.container;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.BuildConfig;
import com.github.mrvilkaman.di.DaggerMyAppComponent;
import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

	@Inject
	DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

	@NonNull
	public static App get(@NonNull Context context) {
		return (App) context.getApplicationContext();
	}
	@Override
	public void onCreate() {
		super.onCreate();
		CleanBaseSettings.Builder builder = CleanBaseSettings.changeSettings();
		builder.setSubscribeLogs(BuildConfig.DEBUG)
				.setHttpLogging(BuildConfig.DEBUG)
				.setRxBusLogs(BuildConfig.DEBUG)
				.setImageLoadingLogs(BuildConfig.DEBUG);
		CleanBaseSettings.save(builder);


		DaggerMyAppComponent.builder().context(this).build().inject(this);
//		DaggerAppComponent.builder()
//				.appModule(new AppModule(this))
//				.build().inject(this);
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjector;
	}
}
