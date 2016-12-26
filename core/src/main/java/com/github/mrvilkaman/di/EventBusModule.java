package com.github.mrvilkaman.di;

import android.util.Log;


import com.github.mrvilkaman.presentationlayer.app.CleanBaseSettings;

import net.jokubasdargis.rxbus.AndroidRxBus;
import net.jokubasdargis.rxbus.Bus;
import net.jokubasdargis.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {
	private static final String TAG = "EventBus";

	@Provides
	@Singleton
	public Bus provideBus(){
		// TODO: 25.12.16 mode to global setting
		RxBus.Logger logger = CleanBaseSettings.rxBusLogs() ? message -> Log.d(TAG, message) : null;
		return AndroidRxBus.create(logger);
	}
}
