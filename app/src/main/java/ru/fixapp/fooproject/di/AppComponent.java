package ru.fixapp.fooproject.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.fixapp.fooproject.di.modules.ApiModule;
import ru.fixapp.fooproject.di.modules.AppModule;
import ru.fixapp.fooproject.di.modules.ImageLoaderModule;
import ru.fixapp.fooproject.di.modules.NetworkModule;
import ru.fixapp.fooproject.di.modules.ProvidersModule;

@Component(modules = {
		AppModule.class,
		ApiModule.class,
		NetworkModule.class,
		EventBusModule.class,
		ProvidersModule.class,
		ImageLoaderModule.class})
@Singleton
public interface AppComponent extends AppCoreComponent {
}
