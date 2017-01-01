package com.github.mrvilkaman.di.modules.activity;


import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelperEmpty;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerEmptyModule {

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		return new LeftDrawerHelperEmpty();
	}

}
