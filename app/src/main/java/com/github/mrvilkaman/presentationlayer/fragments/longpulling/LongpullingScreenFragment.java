package com.github.mrvilkaman.presentationlayer.fragments.longpulling;


import android.os.Bundle;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

import butterknife.OnClick;

public class LongpullingScreenFragment extends BaseFragment<LongpullingPresenter>
		implements LongpullingView {


	public static LongpullingScreenFragment open() {
		return new LongpullingScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_longpullingscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@OnClick(R.id.single_some_btn)
	void onClick(){
		getPresenter().doWork();
	}

	@Override
	public void daggerInject() {
		ActivityComponent component = getComponent(ActivityComponent.class);
		DaggerLongpullingScreenComponent.builder()
				.activityComponent(component)
				.build()
				.inject(this);
	}

}