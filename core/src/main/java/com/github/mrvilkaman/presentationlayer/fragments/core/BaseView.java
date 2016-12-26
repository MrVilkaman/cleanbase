package com.github.mrvilkaman.presentationlayer.fragments.core;


import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;

public interface BaseView extends UIResolver {

	BasePresenter getPresenter();

	void showProgress();

	void hideProgress();

	void handleError(Throwable throwable);

}