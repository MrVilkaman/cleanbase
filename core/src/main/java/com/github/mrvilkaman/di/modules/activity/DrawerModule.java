package com.github.mrvilkaman.di.modules.activity;


import android.support.v7.app.AppCompatActivity;

import com.github.mrvilkaman.di.INeedActivityViewNotify;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module
public class DrawerModule {



	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper(AppCompatActivity activity) {
		return new LeftDrawerHelperImpl(activity);
	}

	@IntoMap
	@IntKey(INeedActivityViewNotify.INIT_PRIORITY_FIRST)
	@Provides
	@PerActivity
	public INeedActivityViewNotify
	createLeftDrawerHelperINeedActivityViewNotify(LeftDrawerHelper helper) {
		return (INeedActivityViewNotify) helper;
	}


}
