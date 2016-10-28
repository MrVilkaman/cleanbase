package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public class Frag5ScreenFragment extends FragBaseScreenFragment {


	public static Frag5ScreenFragment open() {
		return new Frag5ScreenFragment();
	}

	@Override
	protected int getNumber() {
		return 5;
	}

	@Override
	protected BaseFragment nextFragment() {
		return null;
	}

	@Override
	public void daggerInject() {
		DaggerFragScreenComponent.builder().activityComponent(getComponent(ActivityComponent.class))
				.build().inject(this);
	}
}
