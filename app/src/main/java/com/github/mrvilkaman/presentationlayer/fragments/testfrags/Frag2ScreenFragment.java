package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public class Frag2ScreenFragment extends FragBaseScreenFragment {


	public static Frag2ScreenFragment open() {
		return new Frag2ScreenFragment();
	}

	@Override
	protected int getNumber() {
		return 2;
	}

	@Override
	protected BaseFragment nextFragment() {
		return Frag3ScreenFragment.open();
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}
}
