package com.github.mrvilkaman.presentationlayer.fragments.longpulling;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;
import dagger.Module;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {LongpullingScreenComponent.LongpullingScreenModule.class})
public interface LongpullingScreenComponent {

	void inject(LongpullingScreenFragment fragment);

	@Module
	class LongpullingScreenModule {

	}
}