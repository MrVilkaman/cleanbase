package com.github.mrvilkaman.presentationlayer.fragments.imageload;

import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class)
public interface ImageloadScreenComponent {

	void inject(ImageloadScreenFragment fragment);
}