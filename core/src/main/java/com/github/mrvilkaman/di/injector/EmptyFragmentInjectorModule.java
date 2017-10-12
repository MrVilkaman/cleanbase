package com.github.mrvilkaman.di.injector;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.mrvilkaman.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;

@Module
public class EmptyFragmentInjectorModule {

	@PerActivity
	@Provides
	@Nullable
	AndroidInjector<Fragment> provideInjector(){
		return null;
	}
}
