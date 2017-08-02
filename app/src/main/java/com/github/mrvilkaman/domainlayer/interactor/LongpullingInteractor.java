package com.github.mrvilkaman.domainlayer.interactor;


import io.reactivex.Observable;

public interface LongpullingInteractor {
	Observable<String> doWorkWithError();

	void doWork();

	Observable<String> doWorkWithResponse();
}
