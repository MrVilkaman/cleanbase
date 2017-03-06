package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomViewContainerScreenFragment extends BaseFragment<CustomViewContainerPresenter>
		implements CustomViewContainerView {

	@BindView(R.id.custom_views) MyCustomWidget customWidget;
	@Inject MyCustomPresenter customWidgetPresenter;
	@Inject FormatterForCustomView formatterForCustomView;

	public static CustomViewContainerScreenFragment open() {
		return new CustomViewContainerScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_customviewcontainerscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
//		customWidget.setFormatterForCustomView(formatterForCustomView);
		customWidget.inject(getBuild());
		attachCustomView(customWidget, customWidgetPresenter);
	}

	@OnClick(R.id.customviewscontainter_button)
	void onClick(){
		getPresenter().doWork();
	}

	@Override
	public void daggerInject() {
		Log.d("QWER","daggerInject");

		getBuild().inject(this);
	}

	public CustomViewContainerScreenComponent getBuild() {
		return  DaggerCustomViewContainerScreenComponent.builder()
				.activityComponent(getComponent(ActivityComponent.class))
				.build();
	}

}