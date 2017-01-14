package com.github.mrvilkaman.presentationlayer.fragments.core;


public interface BaseView  {

	BasePresenter getPresenter();

	void showProgress();

	void hideProgress();

	void handleError(Throwable throwable);

}