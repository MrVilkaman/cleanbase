package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.presentationlayer.utils.IToolbar;

public interface BaseActivityView {

	void showProgress();

	void hideProgress();

	void clearProgress();

	void back();

	void hideKeyboard();

	IToolbar getToolbar();
}
