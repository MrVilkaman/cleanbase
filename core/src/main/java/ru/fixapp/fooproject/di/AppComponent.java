package ru.fixapp.fooproject.di;

import android.content.Context;

import javax.inject.Singleton;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.resolution.ImageLoader;
import ru.fixapp.fooproject.presentationlayer.utils.StorageUtils;

/**
 * Created by Zahar on 24.03.16.
 */
//@Component(modules = {
//		AppModule.class,
//		ApiModule.class,
//		NetworkModule.class,
//		EventBusModule.class,
//		ImageLoaderModule.class,
//		ProvidersModule.class})
@Singleton
public interface AppComponent {

//	SessionDataProvider getSessionDataProvider();

	SchedulersProvider getSchedulersProvider();

//	RestApi provideApi();

	StorageUtils storageUtils();

	Context provideContext();

	ImageLoader provideImageLoader();
}
