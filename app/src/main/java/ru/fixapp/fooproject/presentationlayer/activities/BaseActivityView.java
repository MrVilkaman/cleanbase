package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;

public interface BaseActivityView {

	void showProgress();

	void hideProgress();

	void clearProgress();

	void back();

	void hideKeyboard();

	IToolbar getToolbar();
}
