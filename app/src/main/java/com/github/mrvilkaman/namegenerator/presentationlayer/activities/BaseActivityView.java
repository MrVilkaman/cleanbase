package com.github.mrvilkaman.namegenerator.presentationlayer.activities;

import com.github.mrvilkaman.namegenerator.presentationlayer.utils.IToolbar;

public interface BaseActivityView {

	void showProgress();

	void hideProgress();

	void clearProgress();

	void back();

	void hideKeyboard();

	IToolbar getToolbar();
}
