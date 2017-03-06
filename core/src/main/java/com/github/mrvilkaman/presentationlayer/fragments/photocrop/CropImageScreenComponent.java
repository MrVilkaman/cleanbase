package com.github.mrvilkaman.presentationlayer.fragments.photocrop;

import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class)
public interface CropImageScreenComponent {
	void inject(CropImageFragment fragment);
}