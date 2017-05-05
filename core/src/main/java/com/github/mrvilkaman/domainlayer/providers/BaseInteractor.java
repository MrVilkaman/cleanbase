package com.github.mrvilkaman.domainlayer.providers;


import javax.inject.Inject;

public abstract class BaseInteractor implements IBaseInteractor {

	protected SchedulersProvider schedulers;
	protected GlobalSubscriptionManager manager;

	@Inject
	@Override
	public void setSchedulers(SchedulersProvider schedulers) {
		this.schedulers = schedulers;
	}

	@Inject
	@Override
	public void setManager(GlobalSubscriptionManager manager) {
		this.manager = manager;
	}
}
