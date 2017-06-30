package com.github.mrvilkaman.presentationlayer.fragments.cachework;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.longpulling.LongpullingScreenFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class CacheworkScreenFragment extends BaseFragment<CacheworkPresenter>
		implements CacheworkView {

	@BindView(R.id.text) TextView textView;
	@BindView(R.id.update) Button buttonView;


	public static CacheworkScreenFragment open() {
		return new CacheworkScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_cacheworkscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@Override
	public void bindText(String text) {
		textView.setText(text);
	}

	@Override
	public void daggerInject() {
		ActivityComponent component = getComponent(ActivityComponent.class);
		DaggerCacheworkScreenComponent.builder().activityComponent(component).build().inject(this);
	}

	@OnClick(R.id.update)
	void onClickUpdate() {
		getPresenter().update();
	}

	@OnClick(R.id.screen)
	void onClickOpen() {
		getNavigation().showFragment(LongpullingScreenFragment.open());
	}

	@Override
	public void showProgress() {
		super.showProgress();
		buttonView.setEnabled(false);
	}

	@Override
	public void hideProgress() {
		super.hideProgress();
		buttonView.setEnabled(true);
	}
}