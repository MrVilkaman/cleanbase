package com.github.mrvilkaman.di.modules;

import com.github.mrvilkaman.domainlayer.interactor.LongpullingInteractor;
import com.github.mrvilkaman.domainlayer.interactor.LongpullingInteractorImpl;
import com.github.mrvilkaman.domainlayer.providers.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GlobalInteractors {

	@Provides
	@Singleton
	public LongpullingInteractor getLongpullingInteractor(SchedulersProvider schedulersProvider) {
	    return new LongpullingInteractorImpl(schedulersProvider);
	}
}
