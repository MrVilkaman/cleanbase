package com.github.mrvilkaman.presentationlayer.fragments.photocrop;

import dagger.Component;
import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.di.ActivityCoreComponent;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class)
public interface CropImageScreenComponent {
	void inject(CropImageFragment fragment);

}