package com.github.mrvilkaman.di;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import dagger.Lazy;

@PerActivity
public class LazyActivityInitNotify {

	private final Lazy<Set<INeedActivityViewNotify>> set;

	@Inject
	public LazyActivityInitNotify(Lazy<Set<INeedActivityViewNotify>> set) {
		this.set = set;
	}

	public void onViewCreate() {
		Set<INeedActivityViewNotify> iNeedActivityViewNotifies = set.get();
		ArrayList<INeedActivityViewNotify> iNeedActivityViewNotifies1 = new ArrayList<>(iNeedActivityViewNotifies);
		Collections.sort(iNeedActivityViewNotifies1, (o1, o2) -> o1.getInitPriority() <
				o2.getInitPriority() ? -1 : 1);
		for (INeedActivityViewNotify notify : iNeedActivityViewNotifies1) {
			notify.onInit();
		}
	}
}
