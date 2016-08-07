package ru.fixapp.fooproject.presentationlayer.fragments.hello;
/**
 * Created by root on 12.03.16.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.listsample.ListSampleScreenFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class HelloScreenFragment extends BaseFragment<HelloScreenPresenter> implements HelloScreenView {

	@BindView(R.id.hello_login_btn)
	Button btnLogin;

	public static HelloScreenFragment open() {
		return new HelloScreenFragment();
	}

	@Override
	protected void daggerInject() {
		DaggerHelloScreenComponent.builder()
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
		showFragment(ListSampleScreenFragment.open());
	}
}