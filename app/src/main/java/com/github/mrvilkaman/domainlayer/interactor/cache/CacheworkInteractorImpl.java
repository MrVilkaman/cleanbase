package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.datalayer.provider.CacheworkDp;
import com.github.mrvilkaman.domainlayer.providers.BaseInteractor;

import javax.inject.Inject;

import rx.Observable;

public class CacheworkInteractorImpl extends BaseInteractor implements CacheworkInteractor {

	private CacheworkDp cacheworkDp;

	@Inject
	public CacheworkInteractorImpl(CacheworkDp cacheworkDp) {
		this.cacheworkDp = cacheworkDp;
	}

	@Override
	public Observable<StringDataWrapper> observeSomedata() {
		return cacheworkDp.observeString()
				.doOnSubscribe(() -> cacheworkDp.refreshString())
				.map(value -> new StringDataWrapper(value, cacheworkDp.getCurrentProgress()))
				.onErrorReturn(throwable -> new StringDataWrapper(throwable,
						cacheworkDp.getCurrentProgress()))
				.mergeWith(cacheworkDp.observeStringProgress().map(StringDataWrapper::new));
	}

	@Override
	public void update() {
		cacheworkDp.refreshString();
	}
}
