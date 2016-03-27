package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.hello;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
public class HelloScreenModule {

	@Provides
	@NonNull
	HelloScreenPresenter provideHelloScreenPresenter() {
		return new HelloScreenPresenter();
	}
}

