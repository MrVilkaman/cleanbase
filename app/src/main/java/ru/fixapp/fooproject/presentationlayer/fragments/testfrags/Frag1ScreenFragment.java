package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import ru.fixapp.fooproject.presentationlayer.activities.ActivityCoreComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

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
