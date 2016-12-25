package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import ru.fixapp.fooproject.presentationlayer.activities.ActivityCoreComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

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
	public void daggerInject(ActivityCoreComponent component) {
		DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}
}
