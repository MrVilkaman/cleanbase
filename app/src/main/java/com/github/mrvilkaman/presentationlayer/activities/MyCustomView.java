package com.github.mrvilkaman.presentationlayer.activities;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;

public interface MyCustomView extends BaseView {

	void changeText();

	IProgressState getButton2Progress();
}