package com.github.mrvilkaman.presentationlayer.activities;

import com.github.mrvilkaman.datalayer.providers.GlobalBusQuery;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.ViewSubscriber;
import com.github.mrvilkaman.utils.bus.Bus;

import javax.inject.Inject;

public class SecondActivityPresenter extends BasePresenter<SecondActivityView> {


	private Bus bus;

	@Inject
	public SecondActivityPresenter(Bus bus) {
		this.bus = bus;
	}

	@Override
	public void onViewAttached() {
		super.onViewAttached();
		subscribeUI(bus.queue(GlobalBusQuery.CURRENT_SCREEN_NAME),new ViewSubscriber<SecondActivityView,String>(){
			@Override
			public void onNext(String screenName) {
				uiResolver().showToast(com.github.mrvilkaman.core.R.string.cleanbase_simple_text,screenName);
			}
		});

		uiResolver().showToast(com.github.mrvilkaman.core.R.string.cleanbase_simple_text,"SecondActivityPresenter - onViewAttached");
	}
}
