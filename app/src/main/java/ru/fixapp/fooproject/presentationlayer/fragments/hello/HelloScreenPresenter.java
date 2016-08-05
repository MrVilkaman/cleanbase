package ru.fixapp.fooproject.presentationlayer.fragments.hello;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {


	public void onClickBtn() {
		view().goToMainScreen();
	}

}
