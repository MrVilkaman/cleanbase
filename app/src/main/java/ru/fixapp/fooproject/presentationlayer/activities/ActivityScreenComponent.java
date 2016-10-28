package ru.fixapp.fooproject.presentationlayer.activities;


import dagger.Component;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;

@PerActivity
@Component(dependencies = AppComponent.class)
public interface ActivityScreenComponent {
	void inject(MainActivity activity);

	SchedulersProvider getSchedulersProvider();

}