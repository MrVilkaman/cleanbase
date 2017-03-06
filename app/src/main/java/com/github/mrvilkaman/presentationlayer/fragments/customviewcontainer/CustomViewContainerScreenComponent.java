package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;

@PerScreen
@Component(dependencies = ActivityComponent.class)
public interface CustomViewContainerScreenComponent {

	void inject(CustomViewContainerScreenFragment fragment);

	void inject(MyCustomWidget fragment);

}