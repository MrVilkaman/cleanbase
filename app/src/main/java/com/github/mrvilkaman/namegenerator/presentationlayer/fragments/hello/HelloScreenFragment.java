package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.hello;
/**
 * Created by root on 12.03.16.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mrvilkaman.namegenerator.R;
import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class HelloScreenFragment extends BaseFragment<HelloScreenPresenter> implements HelloScreenView {

	@Bind(R.id.hello_login_btn)
	Button btnLogin;

	public static HelloScreenFragment open() {
		return new HelloScreenFragment();
	}

	@Override
	protected void daggerInject() {
		DaggerHelloComponent.builder()
				.appComponent(getAppComponent())
				.build()
				.inject(this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_helloscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@OnClick(R.id.hello_login_btn)
	void onClick() {
		getPresenter().onClickBtn();
	}


	@Override
	public void goToMainScreen() {
		showMessage("goToMainScreen");
	}
}