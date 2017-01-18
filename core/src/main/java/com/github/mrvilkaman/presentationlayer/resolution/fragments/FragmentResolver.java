package com.github.mrvilkaman.presentationlayer.resolution.fragments;


import android.content.Intent;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public interface FragmentResolver {


	boolean hasFragment();

	void addStaticFragment(int drawerContentFrame, BaseFragment drawerFragment);

	void startActivityForResult(Intent intent, int requestCode);

	void popBackStack();

	void showFragment(BaseFragment fragment);

	void showRootFragment(BaseFragment fragment);

	void showFragmentWithoutBackStack(BaseFragment fragment);

	boolean processBackFragment();

	boolean checkBackStack();

	void setTargetFragmentCode(int code);

	void setCallback(FragmentResolverCallback callback);

	boolean isRootScreen();

	interface FragmentResolverCallback{

		void onRootFragment();

		void onNotRootFragment();
	}
}
