package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import android.os.Bundle;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.DaggerBaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomViewContainerScreenFragment
		extends DaggerBaseFragment<CustomViewContainerPresenter,
		CustomViewContainerScreenComponent>
		implements CustomViewContainerView {

	@BindView(R.id.custom_views) MyCustomWidget customWidget;

	public static CustomViewContainerScreenFragment open() {
		return new CustomViewContainerScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_customviewcontainerscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		attachCustomView(customWidget);
	}

	@OnClick(R.id.customviewscontainter_button)
	void onClick() {
		getPresenter().doWork();
	}

	@Override
	public void injectMe(CustomViewContainerScreenComponent screenComponent) {
		screenComponent.inject(this);
	}

	@Override
	protected CustomViewContainerScreenComponent createComponent() {
		return DaggerCustomViewContainerScreenComponent.builder()
				.activityComponent(getComponent(ActivityComponent.class))
				.build();
	}
}