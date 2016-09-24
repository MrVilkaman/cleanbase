package ru.fixapp.fooproject.presentationlayer.activities;


import dagger.Component;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.PerActivity;

@PerActivity
@Component(dependencies = AppComponent.class)
interface ActivityScreenComponent {
	void inject(MainActivity activity);
}