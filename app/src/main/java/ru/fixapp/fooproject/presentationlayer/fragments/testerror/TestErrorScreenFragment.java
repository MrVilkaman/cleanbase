package ru.fixapp.fooproject.presentationlayer.fragments.testerror;


import android.os.Bundle;
import android.view.View;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public class TestErrorScreenFragment extends BaseFragment<TestErrorPresenter>
		implements TestErrorView {


	public static TestErrorScreenFragment open() {
		return new TestErrorScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_testerrorscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@Override
	public void daggerInject(ActivityComponent component) {
		DaggerTestErrorScreenComponent.builder()
				.activityComponent(component)
				.build()
				.inject(this);
	}

}