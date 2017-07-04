package com.github.mrvilkaman.domainlayer.interactor.cache;


import rx.Observable;

public interface CacheworkInteractor {

	Observable<StringDataWrapper> observeSomedata();

	void update();

	void update2();

}
