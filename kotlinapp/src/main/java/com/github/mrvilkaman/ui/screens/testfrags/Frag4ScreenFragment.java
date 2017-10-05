package com.github.mrvilkaman.ui.screens.testfrags;

import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public class Frag4ScreenFragment extends FragBaseScreenFragment {


	public static Frag4ScreenFragment open() {
		return new Frag4ScreenFragment();
	}

	@Override
	protected int getNumber() {
		return 4;
	}

	@Override
	protected BaseFragment nextFragment() {
		return Frag5ScreenFragment.Companion.open();
	}

	@Override
	protected int getIcon() {
		return android.R.drawable.ic_dialog_map;
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}
}
