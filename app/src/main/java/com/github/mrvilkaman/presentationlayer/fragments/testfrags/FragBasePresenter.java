package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.ViewSubscriber;

import javax.inject.Inject;

import rx.Observable;

public class FragBasePresenter extends BasePresenter {
	@Inject
	public FragBasePresenter() {
	}

	@Override
	public void onViewAttached() {
		super.onViewAttached();
		subscribe(Observable.error(new RuntimeException()), new ViewSubscriber<>(view()));
	}
}
