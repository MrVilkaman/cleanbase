package com.github.mrvilkaman.presentationlayer.fragments.core;


public interface BaseView  {

	void showProgress();

	void hideProgress();

	void handleError(Throwable throwable);

}