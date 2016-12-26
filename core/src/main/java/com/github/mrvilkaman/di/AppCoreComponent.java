package com.github.mrvilkaman.di;

import android.content.Context;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;
import com.github.mrvilkaman.presentationlayer.utils.StorageUtils;

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
