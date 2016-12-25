package ru.fixapp.fooproject.di.modules.activity;


import android.view.View;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelperImpl;

@Module
public class DrawerModule {

	protected View view;

	public DrawerModule(View view) {
		this.view = view;
	}

	@Provides
	@PerActivity
	public LeftDrawerHelper createLeftDrawerHelper() {
		return new LeftDrawerHelperImpl(view) {
			@Override
			public BaseFragment getDrawerFragment() {
				//// TODO: 25.12.16 libcore need fix!
				return null;
//				return DrawerScreenFragment.open();
			}
		};
	}

}
