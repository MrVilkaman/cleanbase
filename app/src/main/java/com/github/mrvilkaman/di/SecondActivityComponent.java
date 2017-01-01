package com.github.mrvilkaman.di;

import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.activities.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.activities.SecondActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, DrawerModule.class,
				ToolbarModule.class})
public interface SecondActivityComponent extends ActivityCoreComponent {

	void inject(SecondActivity activity);
}
