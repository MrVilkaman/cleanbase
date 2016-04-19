package com.github.mrvilkaman.namegenerator.presentationlayer.app;

import com.github.mrvilkaman.namegenerator.datalayer.api.ApiModule;
import com.github.mrvilkaman.namegenerator.datalayer.api.RestApi;
import com.github.mrvilkaman.namegenerator.datalayer.modules.ProvidersModule;
import com.github.mrvilkaman.namegenerator.datalayer.modules.NetworkModule;
import com.github.mrvilkaman.namegenerator.domainlayer.providers.SchedulersProvider;
import com.github.mrvilkaman.namegenerator.domainlayer.providers.SessionDataProvider;
import com.github.mrvilkaman.namegenerator.presentationlayer.activities.MainActivity;

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
