package com.github.mrvilkaman.presentationlayer.activities;

import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;

public interface BaseActivityView extends IProgressState {

	void hideKeyboard();

	boolean isTaskRoot();
}
