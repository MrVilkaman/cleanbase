package ru.fixapp.fooproject.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.fixapp.fooproject.datalayer.api.RestApi;
import ru.fixapp.fooproject.di.modules.ApiModule;
import ru.fixapp.fooproject.di.modules.AppModule;
import ru.fixapp.fooproject.di.modules.ImageLoaderModule;
import ru.fixapp.fooproject.di.modules.NetworkModule;
import ru.fixapp.fooproject.di.modules.ProvidersModule;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.domainlayer.providers.SessionDataProvider;
import ru.fixapp.fooproject.presentationlayer.resolution.ImageLoader;

/**
 * Created by Zahar on 24.03.16.
 */
@Component(modules = {
		AppModule.class,
		ApiModule.class,
		NetworkModule.class,
		ImageLoaderModule.class,
		ProvidersModule.class})
@Singleton
public interface AppComponent {

	SessionDataProvider getSessionDataProvider();

	SchedulersProvider getSchedulersProvider();

	RestApi provideApi();

	Context provideContext();

	ImageLoader provideImageLoader();
}
