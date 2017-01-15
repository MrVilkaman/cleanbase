package com.github.mrvilkaman.presentationlayer.activities;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import javax.inject.Inject;

public class SecondActivityPresenter extends BasePresenter<SecondActivityView> {


	@Inject
	public SecondActivityPresenter() {
	}

	@Override
	public void onViewAttached() {
		super.onViewAttached();
		uiResolver().showToast(com.github.mrvilkaman.core.R.string.cleanbase_simple_text,"SecondActivityPresenter - onViewAttached");
	}
}
