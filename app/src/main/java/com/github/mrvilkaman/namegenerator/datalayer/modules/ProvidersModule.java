package com.github.mrvilkaman.namegenerator.datalayer.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mrvilkaman.namegenerator.datalayer.providers.MainSchedulersProvider;
import com.github.mrvilkaman.namegenerator.datalayer.providers.SessionDataProviderImpl;
import com.github.mrvilkaman.namegenerator.datalayer.store.LocalStorage;
import com.github.mrvilkaman.namegenerator.datalayer.store.LocalStorageImpl;
import com.github.mrvilkaman.namegenerator.datalayer.store.MemoryStorage;
import com.github.mrvilkaman.namegenerator.datalayer.store.MemoryStorageImpl;
import com.github.mrvilkaman.namegenerator.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.namegenerator.domainlayer.providers.SessionDataProvider;

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
	SessionDataProvider provideSessionDataProvider(LocalStorage localStorage, MemoryStorage memoryStorage) {
		return new SessionDataProviderImpl(localStorage, memoryStorage);
	}

	@Singleton
	@Provides
	SchedulersProvider provideSchedulersProvider() {
		return new MainSchedulersProvider();
	}

	@Singleton
	@Provides
	@NonNull
	LocalStorage provideLocalStorage(Context context) {
		return new LocalStorageImpl(context);
	}

	@Singleton
	@Provides
	@NonNull
	MemoryStorage provideMemoryStorage() {
		return new MemoryStorageImpl();
	}

}
