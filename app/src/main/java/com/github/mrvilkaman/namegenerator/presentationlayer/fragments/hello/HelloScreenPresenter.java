package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.hello;

import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BasePresenter;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {


	public void onClickBtn() {
		view().goToMainScreen();
	}

}
