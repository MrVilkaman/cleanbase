package ru.fixapp.fooproject.presentationlayer.activities;


import dagger.Component;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.di.modules.activity.CommonActivityModule;
import ru.fixapp.fooproject.di.modules.activity.DrawerModule;
import ru.fixapp.fooproject.di.modules.activity.ToolbarModule;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, DrawerModule.class,
				ToolbarModule.class})
public interface ActivityComponent extends ActivityCoreComponent {

	void inject(MainActivity activity);

}
