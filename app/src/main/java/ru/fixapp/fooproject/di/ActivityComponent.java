package ru.fixapp.fooproject.di;


import dagger.Component;
import ru.fixapp.fooproject.di.modules.activity.CommonActivityModule;
import ru.fixapp.fooproject.di.modules.activity.DrawerModule;
import ru.fixapp.fooproject.di.modules.activity.ToolbarModule;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityCoreComponent;
import ru.fixapp.fooproject.presentationlayer.activities.MainActivity;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, DrawerModule.class,
				ToolbarModule.class})
public interface ActivityComponent extends ActivityCoreComponent {

	void inject(MainActivity activity);

}
