package com.github.mrvilkaman.di;


import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerModule;
import com.github.mrvilkaman.di.modules.activity.FragmentModule;
import com.github.mrvilkaman.di.modules.activity.ThrowableModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.activities.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, ThrowableModule.class,
				FragmentModule.class, DrawerModule.class, ToolbarModule.class})
public interface ActivityComponent {

	void inject(MainActivity activity);
}
