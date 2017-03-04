package com.github.mrvilkaman.di.modules.activity;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerEmptyModule {

	@Provides
	@PerActivity
	@Nullable
	public static LeftDrawerHelper createLeftDrawerHelper() {
		return null;
	}

}
