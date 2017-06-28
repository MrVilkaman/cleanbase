package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.domainlayer.providers.BaseInteractor;

import javax.inject.Inject;

import rx.Observable;

public class CacheworkInteractorImpl extends BaseInteractor implements CacheworkInteractor {

	@Inject
	public CacheworkInteractorImpl() {

	}

	@Override
	public Observable<StringDataWrapper> observeSomedata() {
		return Observable.just(new StringDataWrapper("КУ-КУ"));
	}

	@Override
	public void update() {

	}
}
