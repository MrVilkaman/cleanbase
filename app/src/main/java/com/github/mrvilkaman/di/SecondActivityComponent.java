package com.github.mrvilkaman.di;

import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerEmptyModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.activities.SecondActivity;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, DrawerEmptyModule.class, ToolbarModule.class,
				SecondActivityComponent.ActivityModule.class})
public interface SecondActivityComponent extends ActivityCoreComponent {

	void inject(SecondActivity activity);

	@Module
	class ActivityModule {

		@Provides
		@PerActivity
		public BasePresenter getBasePresenter() {
			return new BasePresenter();
		}
	}
}
