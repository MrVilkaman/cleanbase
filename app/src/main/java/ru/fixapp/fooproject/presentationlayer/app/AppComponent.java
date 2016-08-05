package ru.fixapp.fooproject.presentationlayer.app;

import ru.fixapp.fooproject.datalayer.api.ApiModule;
import ru.fixapp.fooproject.datalayer.api.RestApi;
import ru.fixapp.fooproject.datalayer.modules.ProvidersModule;
import ru.fixapp.fooproject.datalayer.modules.NetworkModule;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.domainlayer.providers.SessionDataProvider;
import ru.fixapp.fooproject.presentationlayer.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@Component(modules = {
		AppModule.class,
		ApiModule.class,
		NetworkModule.class,
		ProvidersModule.class})
@Singleton
public interface AppComponent {

	SessionDataProvider getSessionDataProvider();

	SchedulersProvider getSchedulersProvider();

	RestApi provideApi();

	void inject(App app);

	void inject(MainActivity mainActivity);
}
