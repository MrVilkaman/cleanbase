package com.github.mrvilkaman.di;

import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerEmptyModule;
import com.github.mrvilkaman.di.modules.activity.FragmentModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.activities.SecondActivity;
import com.github.mrvilkaman.presentationlayer.activities.single.SingleActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class,FragmentModule.class, DrawerEmptyModule.class, ToolbarModule.class})
public interface SecondActivityComponent extends ActivityCoreComponent,CustomWidgetComponent {

	void inject(SecondActivity activity);

	void inject(SingleActivity activity);
}
