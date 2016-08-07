package ru.fixapp.fooproject.presentationlayer.fragments.hello;

import javax.inject.Inject;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {

	@Inject
	public HelloScreenPresenter(SchedulersProvider schedulersProvider) {
		super(schedulersProvider);
	}

	public void onClickBtn() {
		view().goToMainScreen();
	}

}
