package com.github.mrvilkaman.di.modules.activity;


import android.app.Activity;

import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.ProvideFragmentCallback;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerModule {

	private Activity activity;
	private ProvideFragmentCallback callback;
	private boolean needSlide;

	public DrawerModule(Activity activity, ProvideFragmentCallback callback) {
		this(activity, callback, true);
	}

	public DrawerModule(Activity activity, ProvideFragmentCallback callback, boolean needSlide) {
		this.activity = activity;
		this.callback = callback;
		this.needSlide = needSlide;
	}

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		LeftDrawerHelperImpl leftDrawerHelper = new LeftDrawerHelperImpl(callback);
		leftDrawerHelper.setNeedDrawerSlide(needSlide);
		leftDrawerHelper.init(activity.findViewById(android.R.id.content));
		return leftDrawerHelper;
	}


}
