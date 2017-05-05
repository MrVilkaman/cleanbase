package com.github.mrvilkaman.domainlayer.providers;


public interface IBaseInteractor {
	void setSchedulers(SchedulersProvider schedulers);

	void setManager(GlobalSubscriptionManager manager);
}
