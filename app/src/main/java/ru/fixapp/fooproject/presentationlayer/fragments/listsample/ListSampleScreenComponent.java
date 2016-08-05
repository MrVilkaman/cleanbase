package ru.fixapp.fooproject.presentationlayer.fragments.listsample;


import ru.fixapp.fooproject.presentationlayer.app.AppComponent;
import ru.fixapp.fooproject.presentationlayer.app.PerActivity;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ListSampleScreenModule.class})
public interface ListSampleScreenComponent {
	void inject(ListSampleScreenFragment fragment);
}