package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public class Frag3ScreenFragment extends FragBaseScreenFragment {


	public static Frag3ScreenFragment open() {
		return new Frag3ScreenFragment();
	}

	@Override
	protected int getNumber() {
		return 3;
	}

	@Override
	protected BaseFragment nextFragment() {
		return Frag4ScreenFragment.open();
	}

	@Override
	protected int getIcon() {
		return android.R.drawable.ic_delete;
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}
}
