package ru.fixapp.fooproject.presentationlayer.fragments.hello;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerScreen;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;

@Component(dependencies = ActivityComponent.class, modules = {HelloScreenComponent.HelloScreenModule.class})
@PerScreen
public interface HelloScreenComponent {
	void inject(HelloScreenFragment fragment);

	@Module
	class HelloScreenModule {
		@Provides
		@PerScreen
		HelloScreenPresenter provideHelloPresenter(SchedulersProvider schedulersProvider) {
			return new HelloScreenPresenter(schedulersProvider);
		}
	}
}