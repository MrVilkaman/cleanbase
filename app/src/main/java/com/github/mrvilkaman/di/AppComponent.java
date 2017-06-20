package com.github.mrvilkaman.di;


import com.github.mrvilkaman.di.modules.ApiModule;
import com.github.mrvilkaman.di.modules.AppModule;
import com.github.mrvilkaman.di.modules.CoreProvidersModule;
import com.github.mrvilkaman.di.modules.EventBusModule;
import com.github.mrvilkaman.di.modules.GlobalInteractors;
import com.github.mrvilkaman.di.modules.ImageLoaderModule;
import com.github.mrvilkaman.di.modules.MyDevModule;
import com.github.mrvilkaman.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class, NetworkModule.class, EventBusModule.class,
		CoreProvidersModule.class, MyDevModule.class, GlobalInteractors.class, ImageLoaderModule.class})
@Singleton
public interface AppComponent extends AppCoreComponent, CommonComponent {
}
