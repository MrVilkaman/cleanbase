package com.github.mrvilkaman.presentationlayer.fragments.longpulling;

import com.github.mrvilkaman.domainlayer.interactor.LongpullingInteractor;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import javax.inject.Inject;

public class LongpullingPresenter extends BasePresenter<LongpullingView> {

	private final LongpullingInteractor interactor;

	@Inject
	public LongpullingPresenter(LongpullingInteractor interactor) {
		this.interactor = interactor;
	}

	public void doWork() {

	}
}
