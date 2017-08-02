package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.LoadSubscriber;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;


public class CustomViewContainerPresenter extends BasePresenter<CustomViewContainerView> {

	@Inject
	public CustomViewContainerPresenter() {

	}

	public void doWork() {
		subscribeUI(
				Observable.just(true).delay(2, TimeUnit.SECONDS), new LoadSubscriber<>());
	}
}
