package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.listsample;


import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BasePresenter;

import java.util.Arrays;

public class ListSamplePresenter extends BasePresenter<ListSampleView> {

	public void loadItems() {
		view().bingItems(Arrays.asList("1","2","3","4"));
	}
}
