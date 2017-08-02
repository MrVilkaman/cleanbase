package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.datalayer.provider.CacheworkDp;
import com.github.mrvilkaman.domainlayer.providers.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.Observable;


public class CacheworkInteractorImpl extends BaseInteractor implements CacheworkInteractor {

	private CacheworkDp cacheworkDp;

	@Inject
	public CacheworkInteractorImpl(CacheworkDp cacheworkDp) {
		this.cacheworkDp = cacheworkDp;
	}

	@Override
	public Observable<StringDataWrapper> observeSomedata() {
		Observable<StringDataWrapper> simpleStringObs = cacheworkDp.observeString()
				.doOnSubscribe(disposable -> cacheworkDp.refreshString())
				.map(StringDataWrapper::fromString);

		Observable<StringDataWrapper> wrapperdStringObs =
				cacheworkDp.observeStringModel().map(StringDataWrapper::fromStringWrapper);

		return simpleStringObs.mergeWith(wrapperdStringObs);
	}

	@Override
	public void update() {
		cacheworkDp.refreshString();
	}

	@Override
	public void update2() {
		cacheworkDp.refreshStringWrapper();
	}
}
