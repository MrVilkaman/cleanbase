package com.github.mrvilkaman.di.injector;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.mrvilkaman.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.DispatchingAndroidInjector;

@Module
public class EmptyFragmentInjectorModule {

	@PerActivity
	@Provides
	@Nullable
	DispatchingAndroidInjector<Fragment> provideInjector(){
		return null;
	}
}
