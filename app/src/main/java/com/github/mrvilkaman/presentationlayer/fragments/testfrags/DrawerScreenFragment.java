package com.github.mrvilkaman.presentationlayer.fragments.testfrags;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.activities.SecondActivity;
import com.github.mrvilkaman.presentationlayer.activities.single.SingleActivity;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.ISingletonFragment;
import com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer
		.CustomViewContainerScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.imageload.ImageloadScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.legacyfragment.LegacyFragment;
import com.github.mrvilkaman.presentationlayer.fragments.longpulling.LongpullingScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.photomaker.PhotoMakerScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.simplelist.SimpleListScreenFragment;

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
		getNavigation().showRootFragment(SimpleListScreenFragment.open());
	}

	@OnClick(R.id.menu_5)
	void onClick5(){
		getNavigation().startActivityForResult(new Intent(getContext(),SecondActivity.class),-1);
	}

	@OnClick(R.id.menu_single_activity)
	void onClickSingleActivity(){
		getNavigation().startActivityForResult(new Intent(getContext(),SingleActivity.class),-1);
	}

	@OnClick(R.id.menu_6)
	void onClick6(){
		getNavigation().showRootFragment(LongpullingScreenFragment.open());
	}

	@OnClick(R.id.menu_7)
	void onClick7(){
		getNavigation().showRootFragment(new LegacyFragment());
	}

	@OnClick(R.id.menu_8)
	void onClick8(){
		getNavigation().showRootFragment(CustomViewContainerScreenFragment.open());
	}


	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerFragScreenComponent.builder().activityCoreComponent(component)
				.build().inject(this);

	}
}
