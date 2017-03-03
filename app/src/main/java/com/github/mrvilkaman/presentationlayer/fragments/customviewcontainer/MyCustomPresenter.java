package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;

import com.github.mrvilkaman.di.PerScreen;
import com.github.mrvilkaman.presentationlayer.activities.MyCustomView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.LoadSubscriber;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

@PerScreen
public class MyCustomPresenter extends BasePresenter<MyCustomView> {

	@Inject
	public MyCustomPresenter() {

	}

	public void loadValues() {
		subscribeUI(Observable.just(true).delay(4, TimeUnit.SECONDS), new LoadSubscriber<>());
	}
}
