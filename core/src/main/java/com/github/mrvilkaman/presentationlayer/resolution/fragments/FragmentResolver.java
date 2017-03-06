package com.github.mrvilkaman.presentationlayer.resolution.fragments;


import android.content.Intent;

import com.github.mrvilkaman.presentationlayer.fragments.core.IBaseScreen;

public interface FragmentResolver {


	boolean hasFragment();

	void addStaticFragment(int drawerContentFrame, IBaseScreen drawerFragment);

	void startActivityForResult(Intent intent, int requestCode);

	void popBackStack();

	void showFragment(IBaseScreen fragment);

	void showRootFragment(IBaseScreen fragment);

	void showFragmentWithoutBackStack(IBaseScreen fragment);

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
