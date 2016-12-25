package ru.fixapp.fooproject.di;

import android.content.Context;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.resolution.ImageLoader;
import ru.fixapp.fooproject.presentationlayer.utils.StorageUtils;

/**
 * Created by Zahar on 24.03.16.
 */

public interface AppCoreComponent {

//	SessionDataProvider getSessionDataProvider();

	SchedulersProvider getSchedulersProvider();

//	RestApi provideApi();

	StorageUtils storageUtils();

	Context provideContext();

	ImageLoader provideImageLoader();
}
