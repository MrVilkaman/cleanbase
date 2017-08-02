package com.github.mrvilkaman.di.modules;

import android.util.Log;

import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;
import com.github.mrvilkaman.utils.bus.AndroidRxBus;
import com.github.mrvilkaman.utils.bus.Bus;
import com.github.mrvilkaman.utils.bus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {
	private static final String TAG = "EventBus";

	@Provides
	@Singleton
	public Bus provideBus(){
		RxBus.Logger logger = CleanBaseSettings.rxBusLogs() ? message -> Log.d(TAG, message) : null;
		return AndroidRxBus.create(logger);
	}
}
