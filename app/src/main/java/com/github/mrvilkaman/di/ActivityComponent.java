package com.github.mrvilkaman.di;


import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.activities.MainActivity;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, DrawerModule.class, ToolbarModule.class,
				ActivityComponent.ActivityModule.class})
public interface ActivityComponent extends ActivityCoreComponent {

	void inject(MainActivity activity);

	@Module
	class ActivityModule {

		@Provides
		@PerActivity
		public BasePresenter getBasePresenter() {
			return new BasePresenter();
		}
	}
}
