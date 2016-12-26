package com.github.mrvilkaman.presentationlayer.fragments.core.photocrop;

import dagger.Component;
import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.presentationlayer.activities.ActivityCoreComponent;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class)
public interface CropImageScreenComponent {
	void inject(CropImageFragment fragment);

}