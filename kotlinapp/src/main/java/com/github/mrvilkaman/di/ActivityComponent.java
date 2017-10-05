package com.github.mrvilkaman.di;


import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerEmptyModule;
import com.github.mrvilkaman.di.modules.activity.FragmentModule;
import com.github.mrvilkaman.di.modules.activity.ThrowableModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarEmptyModule;
import com.github.mrvilkaman.ui.container.MainActivity;
import com.github.mrvilkaman.ui.screens.drawer.DrawerScreenFragment;
import com.github.mrvilkaman.ui.screens.start.StartScreenFragment;

import org.jetbrains.annotations.NotNull;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {
				CommonActivityModule.class,
				ThrowableModule.class,
				FragmentModule.class,
				ToolbarEmptyModule.class,
				DrawerEmptyModule.class})
public interface ActivityComponent extends ActivityCoreComponent {
	void inject(@NotNull MainActivity mainActivity);

	void inject(DrawerScreenFragment drawerScreenFragment);

	void inject(StartScreenFragment startScreenFragment);
}
