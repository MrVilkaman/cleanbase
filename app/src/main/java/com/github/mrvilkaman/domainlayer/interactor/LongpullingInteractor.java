package com.github.mrvilkaman.domainlayer.interactor;

import rx.Observable;

public interface LongpullingInteractor {
	Observable<String> doWorkWithError();

	void doWork();

	Observable<String> doWorkWithResponse();
}
