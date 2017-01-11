package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class SimpleListPresenter extends BasePresenter<SimpleListView> {

	private final ArrayList<String> strings;

	@Inject
	public SimpleListPresenter() {

		List<String> ts = Arrays.asList("1", "2", "3", "1", "2", "3");
		strings = new ArrayList<>(ts);
	}

	@Override
	public void onViewAttached() {
		super.onViewAttached();

		view().bind(strings);
	}

	public void add() {
		strings.add(UUID.randomUUID()
				.toString());
		Collections.shuffle(strings);
		view().bind(strings);
	}
}
