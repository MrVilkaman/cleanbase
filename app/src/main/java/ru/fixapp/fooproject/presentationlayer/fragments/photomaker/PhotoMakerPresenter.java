package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;


import javax.inject.Inject;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;

public class PhotoMakerPresenter extends BasePresenter<PhotoMakerView> {

	@Inject
	public PhotoMakerPresenter(SchedulersProvider schedulersProvider) {
		super(schedulersProvider);
	}
}
