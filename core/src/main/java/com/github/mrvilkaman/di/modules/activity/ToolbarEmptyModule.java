package com.github.mrvilkaman.di.modules.activity;


import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolverEmpty;

import dagger.Module;
import dagger.Provides;

@Module
public class ToolbarEmptyModule {

	@Provides
	@PerActivity
	public ToolbarResolver getToolbarResolver() {
		return new ToolbarResolverEmpty();
	}

	@Provides
	@PerActivity
	public IToolbar getToolbar(ToolbarResolver resolver) {
		return resolver;
	}
}
