package com.github.mrvilkaman.di.modules.activity;


import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolverImpl;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class ThrowableModule {

	@Provides
	@PerActivity
	public ThrowableResolver createThrowableResolver(UIResolver ui) {
		return new ThrowableResolverImpl(ui);
	}

}
