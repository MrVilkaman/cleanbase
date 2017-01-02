package com.github.mrvilkaman.presentationlayer.fragments.imageload;

import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.presentationlayer.activities.ActivityCoreComponent;

import dagger.Component;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class)
public interface ImageloadScreenComponent {

	void inject(ImageloadScreenFragment fragment);
}