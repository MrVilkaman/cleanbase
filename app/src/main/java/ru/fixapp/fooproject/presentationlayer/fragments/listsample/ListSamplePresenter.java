package ru.fixapp.fooproject.presentationlayer.fragments.listsample;


import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;

import java.util.Arrays;

public class ListSamplePresenter extends BasePresenter<ListSampleView> {

	public void loadItems() {
		view().bingItems(Arrays.asList("1","2","3","4"));
	}
}
