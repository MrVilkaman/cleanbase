package ru.fixapp.fooproject.presentationlayer.resolution;

import android.support.v4.app.FragmentActivity;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface NavigationResolver {
	void showFragment(BaseFragment fragment);

	void showRootFragment(BaseFragment fragment);

	void showFragmentWithoutBackStack(BaseFragment fragment);

	BaseFragment createStartFragment();

	boolean onBackPressed();

	void back();

	void openActivity(Class<? extends FragmentActivity> aClass);
}
