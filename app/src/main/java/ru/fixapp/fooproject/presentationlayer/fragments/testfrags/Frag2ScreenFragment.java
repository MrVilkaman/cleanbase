package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

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
	public void daggerInject(ActivityComponent component) {
		DaggerFragScreenComponent.builder().activityComponent(component).build().inject(this);
	}
}
