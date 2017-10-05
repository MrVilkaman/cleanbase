package com.github.mrvilkaman.di;


import com.github.mrvilkaman.di.modules.AppModule;
import com.github.mrvilkaman.di.modules.CoreProvidersModule;
import com.github.mrvilkaman.di.modules.DevModule;
import com.github.mrvilkaman.di.modules.EventBusModule;
import com.github.mrvilkaman.di.modules.ImageLoaderModule;
import com.github.mrvilkaman.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
		//@formatter:off
		AppModule.class,
		DevModule.class,
		NetworkModule.class,
		EventBusModule.class,
		CoreProvidersModule.class,
		ImageLoaderModule.class})
		//@formatter:on
@Singleton
public interface AppComponent extends AppCoreComponent {
}
