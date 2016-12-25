package ru.fixapp.fooproject.di.modules.activity;


import android.view.View;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.presentationlayer.resolution.ProvideFragmentCallback;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

@Module
public class DrawerModule {

	protected View view;
	private ProvideFragmentCallback callback;

	public DrawerModule(View view, ProvideFragmentCallback callback) {
		this.view = view;
		this.callback = callback;
	}

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		return new LeftDrawerHelperImpl(view, callback);
	}

}
