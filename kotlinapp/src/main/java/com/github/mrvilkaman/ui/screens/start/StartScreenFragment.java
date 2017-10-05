package com.github.mrvilkaman.ui.screens.start;


import android.os.Bundle;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public class StartScreenFragment extends BaseFragment<StartPresenter> implements StartView {


	public static StartScreenFragment open() {
		return new StartScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_startscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@Override
	public void daggerInject() {
		ActivityComponent component = getComponent(ActivityComponent.class);
		component.inject(this);
	}


}