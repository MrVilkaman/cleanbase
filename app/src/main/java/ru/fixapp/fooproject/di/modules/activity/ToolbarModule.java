package ru.fixapp.fooproject.di.modules.activity;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.presentationlayer.resolution.ToolbarResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.ToolbarResolverImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;
import ru.fixapp.fooproject.presentationlayer.toolbar.MyToolbarImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;

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
		return new ToolbarResolverImpl(view, activity, menuHelper);
	}

	@Provides
	@PerActivity
	public ToolbarMenuHelper createToolbarMenuHelper() {
		return new ToolbarMenuHelper(() -> activity.invalidateOptionsMenu());
	}

	@Provides
	@PerActivity
	public IToolbar getToolbar(ToolbarMenuHelper toolbarMenuHelper, ToolbarResolver resolver) {
		return new MyToolbarImpl(view, toolbarMenuHelper, resolver);
	}
}
