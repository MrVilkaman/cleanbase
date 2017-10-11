package com.github.mrvilkaman.di.modules.activity;


import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerModule {


	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		LeftDrawerHelperImpl leftDrawerHelper = new LeftDrawerHelperImpl();
//		activity.on
		return leftDrawerHelper;
	}


}
