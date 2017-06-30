package com.github.mrvilkaman.presentationlayer.fragments.cachework;

import com.github.mrvilkaman.domainlayer.interactor.cache.CacheworkInteractor;
import com.github.mrvilkaman.domainlayer.interactor.cache.StringDataWrapper;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.StreamSubscriber;

import javax.inject.Inject;

public class CacheworkPresenter extends BasePresenter<CacheworkView> {

	private CacheworkInteractor interactor;

	@Inject
	public CacheworkPresenter(CacheworkInteractor interactor) {
		this.interactor = interactor;
	}

	@Override
	public void onViewAttached() {
		subscribeUI(interactor.observeSomedata(), new MyStreamSubscriber());
	}

	public void update() {
		interactor.update();
	}

	private static class MyStreamSubscriber
			extends StreamSubscriber<CacheworkView, StringDataWrapper, String> {

		@Override
		public void onNextValue(String value) {
			view().bindText(value);
		}
	}
}
