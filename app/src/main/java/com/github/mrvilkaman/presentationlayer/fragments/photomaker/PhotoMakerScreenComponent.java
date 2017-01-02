package com.github.mrvilkaman.presentationlayer.fragments.photomaker;

import dagger.Component;
import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.di.modules.PhotoHelperModule;
import com.github.mrvilkaman.di.ActivityCoreComponent;

@PerScreen
@Component(dependencies = ActivityCoreComponent.class,
		modules = {PhotoHelperModule.class})
public interface PhotoMakerScreenComponent {
	void inject(PhotoMakerScreenFragment fragment);

}
