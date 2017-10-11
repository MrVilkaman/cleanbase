package com.github.mrvilkaman.di.modules.activity;


import android.support.v7.app.AppCompatActivity;

import com.github.mrvilkaman.datalayer.providers.PermissionManagerImpl;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolverImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonActivityModule {

	@Provides
	@PerActivity
	public UIResolver createUiResolver(AppCompatActivity activity) {
		return new UIResolverImpl(activity, activity.findViewById(android.R.id.content));
	}

	@Provides
	@PerActivity
	public PermissionManager getPermissionManager(AppCompatActivity activity) {
		return new PermissionManagerImpl(activity);
	}

}
