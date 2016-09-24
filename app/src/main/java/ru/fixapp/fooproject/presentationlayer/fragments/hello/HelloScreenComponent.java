package ru.fixapp.fooproject.presentationlayer.fragments.hello;

import android.support.annotation.NonNull;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.PerActivity;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {HelloScreenComponent.HelloScreenModule.class})
public interface HelloScreenComponent {
	void inject(HelloScreenFragment fragment);

	@Module
	class HelloScreenModule {
		@Provides
		@NonNull
		HelloScreenPresenter provideHelloPresenter(SchedulersProvider schedulersProvider) {
			return new HelloScreenPresenter(schedulersProvider);
		}
	}
}