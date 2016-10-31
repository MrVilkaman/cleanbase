package ru.fixapp.fooproject.presentationlayer.resolution;


import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface FragmentResolver {


	boolean hasFragment();

	void addDrawer(int drawerContentFrame, BaseFragment drawerFragment);

	interface FragmentResolverCallback{

		void onRootFragment();

		void onNotRootFragment();
	}

	void showFragment(BaseFragment fragment);

	void showRootFragment(BaseFragment fragment);

	void showFragmentWithoutBackStack(BaseFragment fragment);

	boolean onBackPressed();

	void back();

	void setCallback(FragmentResolverCallback callback);

	boolean isRootScreen();
}
