package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public class Frag1ScreenFragment extends FragBaseScreenFragment {


	public static Frag1ScreenFragment open() {
		return new Frag1ScreenFragment();
	}

	@Override
	protected int getNumber() {
		return 1;
	}

	@Override
	protected BaseFragment nextFragment() {
		return Frag2ScreenFragment.open();
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}
}
