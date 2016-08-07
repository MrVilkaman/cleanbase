package ru.fixapp.fooproject.presentationlayer.fragments.listsample;


import java.util.Arrays;

import javax.inject.Inject;

import ru.fixapp.fooproject.domainlayer.providers.SchedulersProvider;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;

public class ListSamplePresenter extends BasePresenter<ListSampleView> {

	@Inject
	public ListSamplePresenter(SchedulersProvider schedulersProvider) {
		super(schedulersProvider);
	}

	public void loadItems() {
		view().bingItems(Arrays.asList("1","2","3","4"));
	}
}
