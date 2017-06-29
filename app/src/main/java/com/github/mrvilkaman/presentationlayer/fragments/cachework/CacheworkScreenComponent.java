package com.github.mrvilkaman.presentationlayer.fragments.cachework;

import com.github.mrvilkaman.datalayer.provider.CacheworkDp;
import com.github.mrvilkaman.datalayer.provider.CacheworkDpImpl;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.domainlayer.interactor.cache.CacheworkInteractor;
import com.github.mrvilkaman.domainlayer.interactor.cache.CacheworkInteractorImpl;

import dagger.Binds;
import dagger.Component;
import dagger.Module;

@PerScreen
@Component(dependencies = ActivityComponent.class,
		modules = {CacheworkScreenComponent.CacheworkScreenModule.class})
public interface CacheworkScreenComponent {

	void inject(CacheworkScreenFragment fragment);


	@Module
	abstract class CacheworkScreenModule {
		@Binds
		@PerScreen
		public abstract CacheworkInteractor getCacheworkInteractor(CacheworkInteractorImpl interactor);

		@Binds
		@PerScreen
		public abstract CacheworkDp getCacheworkDp(CacheworkDpImpl interactor);
	}
}