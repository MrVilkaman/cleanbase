package com.github.mrvilkaman.di;


import javax.inject.Singleton;

import dagger.Component;
import com.github.mrvilkaman.di.modules.ApiModule;
import com.github.mrvilkaman.di.modules.AppModule;
import com.github.mrvilkaman.di.modules.ImageLoaderModule;
import com.github.mrvilkaman.di.modules.NetworkModule;
import com.github.mrvilkaman.di.modules.ProvidersModule;

@Component(modules = {
		AppModule.class,
		ApiModule.class,
		NetworkModule.class,
		EventBusModule.class,
		ProvidersModule.class,
		ImageLoaderModule.class})
@Singleton
public interface AppComponent extends AppCoreComponent {
}
