package com.github.mrvilkaman.di;


import com.github.mrvilkaman.di.modules.ApiModule;
import com.github.mrvilkaman.di.modules.AppModule;
import com.github.mrvilkaman.di.modules.DevModule;
import com.github.mrvilkaman.di.modules.EventBusModule;
import com.github.mrvilkaman.di.modules.ImageLoaderModule;
import com.github.mrvilkaman.di.modules.NetworkModule;
import com.github.mrvilkaman.di.modules.ProvidersModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
		AppModule.class,
		ApiModule.class,
		NetworkModule.class,
		EventBusModule.class,
		ProvidersModule.class,
		DevModule.class,
		ImageLoaderModule.class})
@Singleton
public interface AppComponent extends AppCoreComponent {
}
