package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.ISingletonFragment;

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
		getNavigation().showRootFragment(Frag2ScreenFragment.open());
	}

	@OnClick(R.id.menu_3)
	void onClick3(){
		getNavigation().showRootFragment(Frag3ScreenFragment.open());
	}

	@OnClick(R.id.menu_4)
	void onClick4(){
		getNavigation().showRootFragment(Frag4ScreenFragment.open());
	}

	@OnClick(R.id.menu_5)
	void onClick5(){
		getNavigation().showRootFragment(Frag5ScreenFragment.open());
	}

	@Override
	public void daggerInject(ActivityComponent component) {
		DaggerFragScreenComponent.builder().activityComponent(component)
				.build().inject(this);

	}
}
