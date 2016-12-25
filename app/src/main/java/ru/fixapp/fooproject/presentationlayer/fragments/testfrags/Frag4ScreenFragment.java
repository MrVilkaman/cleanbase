package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import ru.fixapp.fooproject.presentationlayer.activities.ActivityCoreComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

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
		return Frag5ScreenFragment.open();
	}

	@Override
	public void daggerInject(ActivityCoreComponent component) {
		DaggerFragScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}
}