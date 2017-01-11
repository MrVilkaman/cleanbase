package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class SimpleListPresenter extends BasePresenter<SimpleListView> {

	private final List<SimpleModel> strings;

	@Inject
	public SimpleListPresenter() {

		List<SimpleModel> ts = Arrays.asList(new SimpleModel(1, "1"), new SimpleModel(2, "3"));
		strings = new ArrayList<>(ts);
	}

	@Override
	public void onViewAttached() {
		super.onViewAttached();

		view().bind(strings);
	}


	public void remove(SimpleModel category) {
		strings.remove(category);
		ArrayList<SimpleModel> qwer = new ArrayList<>(strings.size());
		for (int i = 0; i < strings.size(); i++) {
			qwer.add(strings.get(i).clone());
		}

		Collections.shuffle(qwer);
		view().bind(qwer);
	}

	public void add() {
		String value = UUID.randomUUID()
				.toString();
		strings.add(new SimpleModel(strings.size(), value));

		ArrayList<SimpleModel> qwer = new ArrayList<>(strings.size());
		for (int i = 0; i < strings.size(); i++) {
			qwer.add(strings.get(i).clone());
		}
		Collections.shuffle(qwer);
		view().bind(qwer);
	}

	public void shuffle() {
		String value = UUID.randomUUID()
				.toString();
		ArrayList<SimpleModel> qwer = new ArrayList<>(strings.size());
		for (int i = 0; i < strings.size(); i++) {
			qwer.add(strings.get(i).clone());
		}
		qwer.get(0).setValue(value);
		Collections.shuffle(qwer);
		view().bind(qwer);
	}

}
