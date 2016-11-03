package ru.fixapp.fooproject.di.modules.activity;


import android.view.View;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.presentationlayer.resolution.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.LeftDrawerHelperEmpty;

@Module
public class DrawerModule {

	private View view;

	public DrawerModule(View view) {
		this.view = view;
	}

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		return new LeftDrawerHelperEmpty();
	}

}
