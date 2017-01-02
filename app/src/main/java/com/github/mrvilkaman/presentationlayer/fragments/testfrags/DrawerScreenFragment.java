package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.activities.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.activities.SecondActivity;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.ISingletonFragment;
import com.github.mrvilkaman.presentationlayer.fragments.imageload.ImageloadScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.photomaker.PhotoMakerScreenFragment;

import butterknife.OnClick;

public class DrawerScreenFragment extends BaseFragment<FragBasePresenter> implements
		ISingletonFragment {


	public static BaseFragment open() {
		return new DrawerScreenFragment();
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@Override
	protected int getLayoutId() {
		return R.layout.drawer;
	}

	@OnClick(R.id.menu_1)
	void onClick1(){
		getNavigation().showRootFragment(Frag1ScreenFragment.open());
	}

	@OnClick(R.id.menu_2)
	void onClick2(){
		getNavigation().showRootFragment(ImageloadScreenFragment.open());
	}

	@OnClick(R.id.menu_3)
	void onClick3(){
		getNavigation().showRootFragment(PhotoMakerScreenFragment.open());
	}

	@OnClick(R.id.menu_4)
	void onClick4(){
//		getNavigation().showRootFragment(Frag4ScreenFragment.open());
	}

	@OnClick(R.id.menu_5)
	void onClick5(){
		getNavigation().startActivityForResult(new Intent(getContext(),SecondActivity.class),-1);
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerFragScreenComponent.builder().activityCoreComponent(component)
				.build().inject(this);

	}
}
