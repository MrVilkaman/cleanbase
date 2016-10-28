package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import javax.inject.Inject;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;

public class FragBasePresenter extends BasePresenter {
	@Inject
	public FragBasePresenter(SchedulersProvider schedulersProvider) {
		super(schedulersProvider);
	}
}
