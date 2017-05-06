package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.CustomWidgetComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;

@PerScreen
@Component(dependencies = ActivityComponent.class)
public interface CustomViewContainerScreenComponent extends CustomWidgetComponent {

	void inject(CustomViewContainerScreenFragment fragment);


}