package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
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
	public void daggerInject() {
		DaggerFragScreenComponent.builder().activityComponent(getComponent(ActivityComponent.class))
				.build().inject(this);
	}
}
