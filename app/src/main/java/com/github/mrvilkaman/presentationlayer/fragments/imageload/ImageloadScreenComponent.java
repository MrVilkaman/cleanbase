package com.github.mrvilkaman.presentationlayer.fragments.imageload;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;
import dagger.Module;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {ImageloadScreenComponent.ImageloadScreenModule.class})
public interface ImageloadScreenComponent {

	void inject(ImageloadScreenFragment fragment);

	@Module
	class ImageloadScreenModule {

	}
}