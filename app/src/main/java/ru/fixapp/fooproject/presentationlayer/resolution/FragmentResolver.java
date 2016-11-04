package ru.fixapp.fooproject.presentationlayer.resolution;


import android.content.Intent;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface FragmentResolver {


	boolean hasFragment();

	void addDrawer(int drawerContentFrame, BaseFragment drawerFragment);

	void startActivityForResult(Intent intent, int requestCode);

	void showFragment(BaseFragment fragment);

	void showRootFragment(BaseFragment fragment);

	void showFragmentWithoutBackStack(BaseFragment fragment);

	boolean processBackFragment();

	boolean onBackPressed();

	void setTargetFragmentCode(int code);

	void back();

	void setCallback(FragmentResolverCallback callback);

	boolean isRootScreen();

	interface FragmentResolverCallback{

		void onRootFragment();

		void onNotRootFragment();
	}
}
