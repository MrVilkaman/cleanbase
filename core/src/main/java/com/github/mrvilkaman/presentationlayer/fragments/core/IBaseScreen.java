package com.github.mrvilkaman.presentationlayer.fragments.core;


public interface IBaseScreen extends BaseView, OnBackPressedListener,IBaseScreenSystemUtils {

	@Deprecated
	@Override
	void showProgress();

	@Deprecated
	@Override
	void hideProgress();
}
