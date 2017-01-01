package com.github.mrvilkaman.presentationlayer.activities;


import android.content.Context;
import android.support.annotation.Nullable;

import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.utils.StorageUtils;


public interface ActivityCoreComponent {

	SchedulersProvider getSchedulersProvider();

	Context getContext();

	UIResolver getUIResolver();

	ThrowableResolver getThrowableResolver();

	NavigationResolver getNavigationResolver();

	@Nullable IToolbar getIToolbar();

	BaseActivityView provideBaseActivityView();

	ImageLoader provideImageLoader();

	StorageUtils storageUtils();

}