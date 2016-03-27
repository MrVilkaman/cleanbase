package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.hello;

import com.github.mrvilkaman.namegenerator.presentationlayer.app.AppComponent;
import com.github.mrvilkaman.namegenerator.presentationlayer.app.PerActivity;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {HelloScreenModule.class})
public interface HelloComponent {
	void inject(HelloScreenFragment fragment);
}
