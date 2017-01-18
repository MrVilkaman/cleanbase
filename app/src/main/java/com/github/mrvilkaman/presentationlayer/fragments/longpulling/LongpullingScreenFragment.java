package com.github.mrvilkaman.presentationlayer.fragments.longpulling;


import android.os.Bundle;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.testfrags.Frag5ScreenFragment;

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

	@OnClick(R.id.single_some_btn_2)
	void onClick2(){
		getPresenter().doWorkWithResponse();
	}

	@OnClick(R.id.single_some_btn_3)
	void onClick3(){
		getPresenter().doWorkWithError();
	}

	@OnClick(R.id.single_next_btn)
	void onClickТуче(){
		getNavigation().showFragment(Frag5ScreenFragment.open());
	}

	@Override
	public void showString(String s) {
		getUiResolver().showMessage(R.string.cleanbase_simple_text,s);
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