package ru.fixapp.fooproject.presentationlayer.activities;


import dagger.Component;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.di.modules.activity.CommonActivityModule;
import ru.fixapp.fooproject.di.modules.activity.DrawerModule;
import ru.fixapp.fooproject.di.modules.activity.ToolbarModule;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;

@PerActivity
@Component(dependencies = AppComponent.class,
		modules = {CommonActivityModule.class, DrawerModule.class,
				ToolbarModule.class})
public interface ActivityComponent {
	void inject(MainActivity activity);

	SchedulersProvider getSchedulersProvider();

	UIResolver getUIResolver();

	ThrowableResolver getThrowableResolver();

	NavigationResolver getNavigationResolver();

	IToolbar getIToolbar();

	BaseActivityView provideBaseActivityView();

}