package com.github.mrvilkaman.di.modules.activity;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mrvilkaman.datalayer.providers.PermissionManagerImpl;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolverImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonActivityModule {

	protected AppCompatActivity activity;
	protected View view;

	public CommonActivityModule(AppCompatActivity activity,
								View view) {
		this.activity = activity;
		this.view = view;
	}

	@Provides
	@PerActivity
	public UIResolver createUiResolver() {
		return new UIResolverImpl(view.getContext(), view);
	}

	@Provides
	@PerActivity
	public PermissionManager getPermissionManager() {
		return new PermissionManagerImpl(activity);
	}

}
