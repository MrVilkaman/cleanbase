package com.github.mrvilkaman.presentationlayer.activities;


import android.content.Context;

import dagger.Component;
import com.github.mrvilkaman.di.AppCoreComponent;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.utils.StorageUtils;


@PerActivity
@Component(dependencies = AppCoreComponent.class,
		modules = {CommonActivityModule.class, DrawerModule.class,
				ToolbarModule.class})
public interface ActivityCoreComponent {
//	void inject(MainActivity activity);

	SchedulersProvider getSchedulersProvider();

	Context getContext();

	UIResolver getUIResolver();

	ThrowableResolver getThrowableResolver();

	NavigationResolver getNavigationResolver();

	IToolbar getIToolbar();

	BaseActivityView provideBaseActivityView();

	ImageLoader provideImageLoader();

	StorageUtils storageUtils();

}