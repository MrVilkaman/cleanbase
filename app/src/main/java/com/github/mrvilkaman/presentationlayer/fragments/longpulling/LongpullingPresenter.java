package com.github.mrvilkaman.presentationlayer.fragments.longpulling;

import android.util.Log;

import com.github.mrvilkaman.domainlayer.interactor.LongpullingInteractor;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.subscriber.ViewSubscriber;

import javax.inject.Inject;

public class LongpullingPresenter extends BasePresenter<LongpullingView> {

	private final LongpullingInteractor interactor;

	@Inject
	public LongpullingPresenter(LongpullingInteractor interactor) {
		this.interactor = interactor;
	}

	public void doWork() {
		interactor.doWork();
	}

	public void doWorkWithResponse() {
		subscribeUI(interactor.doWorkWithResponse(), new LongpullingViewStringViewSubscriber());
	}

	public void doWorkWithError() {
		interactor.doWorkWithError();
	}

	private static class LongpullingViewStringViewSubscriber
			extends ViewSubscriber<LongpullingView, String> {
		@Override
		public void onNext(String s) {
			super.onNext(s);
			Log.d("QWER","doWorkWithResponse onNext= "+s);
			view().showString(s);
		}
	}
}
