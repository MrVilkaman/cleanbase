package com.github.mrvilkaman.domainlayer.interactor;

import rx.Observable;

public interface LongpullingInteractor {
	void doWorkWithError();

	void doWork();

	Observable<String> doWorkWithResponse();
}
