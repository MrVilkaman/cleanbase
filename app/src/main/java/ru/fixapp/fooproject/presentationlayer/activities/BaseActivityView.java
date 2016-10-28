package ru.fixapp.fooproject.presentationlayer.activities;

public interface BaseActivityView {

	void showProgress();

	void hideProgress();

	void clearProgress();

	void back();

	void hideKeyboard();
}
