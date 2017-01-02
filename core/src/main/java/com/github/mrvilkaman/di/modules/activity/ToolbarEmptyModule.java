package com.github.mrvilkaman.di.modules.activity;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class ToolbarEmptyModule {

	@Provides
	@PerActivity
	@Nullable
	public ToolbarResolver getToolbarResolver() {
		return null;
	}

	@Provides
	@PerActivity
	@Nullable
	public IToolbar getToolbar() {
		return null;
	}
}
