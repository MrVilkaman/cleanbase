package com.github.mrvilkaman.di.modules.activity;


import android.view.View;

import dagger.Module;
import dagger.Provides;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.ProvideFragmentCallback;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

@Module
public class DrawerModule {

	protected View view;
	private ProvideFragmentCallback callback;

	public DrawerModule(ProvideFragmentCallback callback) {
		this.callback = callback;
	}

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		return new LeftDrawerHelperImpl(callback);
	}

}
