package ru.fixapp.fooproject.di.modules.activity;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.presentationlayer.activities.ToolbarResolver;
import ru.fixapp.fooproject.presentationlayer.activities.ToolbarResolverImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;
import ru.fixapp.fooproject.presentationlayer.toolbar.MyToolbarImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;

@Module
public class ToolbarModule {
	private View view;
	private AppCompatActivity activity;

	private Runnable updateToolbarButtonsCallback;
	private Toolbar toolbar;
	private MyToolbarImpl.ToolbarCallbacks toolbarCallback;

	public ToolbarModule(View view, AppCompatActivity activity,
						 Runnable updateToolbarButtonsCallback, Toolbar toolbar,
						 MyToolbarImpl.ToolbarCallbacks toolbarCallback) {
		this.view = view;
		this.activity = activity;
		this.updateToolbarButtonsCallback = updateToolbarButtonsCallback;
		this.toolbar = toolbar;
		this.toolbarCallback = toolbarCallback;
	}

	@Provides
	@PerActivity
	public ToolbarResolver getToolbarResolver(ToolbarMenuHelper menuHelper) {
		return new ToolbarResolverImpl(view, activity, menuHelper);
	}

	@Provides
	@PerActivity
	public ToolbarMenuHelper createToolbarMenuHelper() {
		return new ToolbarMenuHelper(updateToolbarButtonsCallback);
	}

	@Provides
	@PerActivity
	public IToolbar getToolbar(ToolbarMenuHelper toolbarMenuHelper) {
		return new MyToolbarImpl(toolbarMenuHelper, toolbar, toolbarCallback);
	}
}
