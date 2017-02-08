package com.github.mrvilkaman.di.modules.activity;


import android.view.View;

import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.ProvideFragmentCallback;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerModule {

	protected View view;
	private ProvideFragmentCallback callback;
	private boolean needSlide;

	public DrawerModule(ProvideFragmentCallback callback) {
		this(callback,true);
	}

	public DrawerModule(ProvideFragmentCallback callback,boolean needSlide) {
		this.callback = callback;
		this.needSlide = needSlide;
	}

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		LeftDrawerHelperImpl leftDrawerHelper = new LeftDrawerHelperImpl(callback);
		leftDrawerHelper.setNeedDrawerSlide(needSlide);
		return leftDrawerHelper;
	}


}
