package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;

import android.util.Log;

import com.github.mrvilkaman.presentationlayer.activities.MyCustomView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;
import com.github.mrvilkaman.presentationlayer.subscriber.LoadSubscriber;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

public class MyCustomPresenter extends BasePresenter<MyCustomView> {

	@Inject
	public MyCustomPresenter() {
		Log.d("QWER", "MyCustomPresenter");
	}

	public void loadValues() {
		subscribeUI(Observable.just(true).delay(2, TimeUnit.SECONDS), new BaseLoadSubscriber());
	}

	public void loadValues2() {
		subscribeUI(Observable.just(true).delay(2, TimeUnit.SECONDS), new CustomLoadSubscriber(view().getButton2Progress()));
	}

	private static class BaseLoadSubscriber extends LoadSubscriber<MyCustomView, Boolean> {
		@Override
		public void onNext(Boolean item) {
			super.onNext(item);
			view().changeText();
		}
	}

	private class CustomLoadSubscriber extends LoadSubscriber<MyCustomView, Boolean> {

		public CustomLoadSubscriber(IProgressState state) {
			super(state);
		}

		@Override
		public void onNext(Boolean item) {
			super.onNext(item);
			view().changeText();
		}
	}
}
