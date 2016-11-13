package ru.fixapp.fooproject.di;

import android.util.Log;

import net.jokubasdargis.rxbus.AndroidRxBus;
import net.jokubasdargis.rxbus.Bus;
import net.jokubasdargis.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.BuildConfig;

@Module
public class EventBusModule {
	private static final String TAG = "EventBus";

	@Provides
	@Singleton
	public Bus provideBus(){
		RxBus.Logger logger = BuildConfig.DEBUG ? message -> Log.d(TAG, message) : null;
		return AndroidRxBus.create(logger);
	}
}
