package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;
import dagger.Module;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {CustomViewContainerScreenComponent.CustomViewContainerScreenModule.class})
public interface CustomViewContainerScreenComponent {

	void inject(CustomViewContainerScreenFragment fragment);


	@Module
	class CustomViewContainerScreenModule {

	}
}