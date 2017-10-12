package com.github.mrvilkaman.di;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;

@PerActivity
public class LazyActivityInitNotify {

	private final Lazy<Map<Integer,INeedActivityViewNotify>> set;

	@Inject
	public LazyActivityInitNotify(Lazy<Map<Integer,INeedActivityViewNotify>> set) {
		this.set = set;
	}

	public void onViewCreate() {
        Map<Integer, INeedActivityViewNotify> integerINeedActivityViewNotifyMap = set.get();

        ArrayList<Integer> integers = new ArrayList<>(integerINeedActivityViewNotifyMap.keySet());
		Collections.sort(integers);
		for (Integer key : integers) {
            integerINeedActivityViewNotifyMap.get(key).onInit();
		}
	}
}
