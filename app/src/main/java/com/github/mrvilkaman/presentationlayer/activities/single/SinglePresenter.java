package com.github.mrvilkaman.presentationlayer.activities.single;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.LoadSubscriber;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SinglePresenter extends BasePresenter<SingleView> {

	@Inject
	public SinglePresenter() {

	}

	public void processNextText() {

		subscribeUI(Observable.fromCallable(() -> UUID.randomUUID()
				.toString())
				.delay(2, TimeUnit.SECONDS), new LoadSubscriber<>());
	}
}
