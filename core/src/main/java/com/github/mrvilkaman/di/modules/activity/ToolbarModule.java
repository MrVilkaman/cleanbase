package com.github.mrvilkaman.di.modules.activity;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mrvilkaman.di.INeedActivityViewNotify;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarMenuHelper;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolverImpl;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ToolbarModule {

	@Provides
	@PerActivity
	public ToolbarResolver getToolbarResolver(ToolbarMenuHelper menuHelper,
	                                          AppCompatActivity activity,
	                                          @Nullable LeftDrawerHelper drawerHelper) {
		return new ToolbarResolverImpl(activity,menuHelper, drawerHelper);
	}

	@IntoSet
	@Provides
	@PerActivity
	public INeedActivityViewNotify
	getToolbarResolverINeedActivityViewNotify(ToolbarResolver helper) {
		return (INeedActivityViewNotify) helper;
	}

	@Provides
	@PerActivity
	public ToolbarMenuHelper createToolbarMenuHelper(AppCompatActivity activity) {
		return new ToolbarMenuHelper(activity);
	}

	@Provides
	@PerActivity
	public IToolbar getToolbar(ToolbarResolver resolver) {
		return resolver;
	}
}
