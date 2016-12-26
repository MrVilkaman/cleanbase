package com.github.mrvilkaman.di.modules.activity;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dagger.Module;
import dagger.Provides;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarMenuHelper;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolverImpl;

@Module
public class ToolbarModule {
	private View view;
	private AppCompatActivity activity;

	public ToolbarModule(View view, AppCompatActivity activity) {
		this.view = view;
		this.activity = activity;
	}

	@Provides
	@PerActivity
	public ToolbarResolver getToolbarResolver(ToolbarMenuHelper menuHelper) {
		return new ToolbarResolverImpl(menuHelper);
	}

	@Provides
	@PerActivity
	public ToolbarMenuHelper createToolbarMenuHelper() {
		return new ToolbarMenuHelper(() -> activity.invalidateOptionsMenu());
	}

	@Provides
	@PerActivity
	public IToolbar getToolbar(ToolbarResolver resolver) {
		return resolver;
	}
}
