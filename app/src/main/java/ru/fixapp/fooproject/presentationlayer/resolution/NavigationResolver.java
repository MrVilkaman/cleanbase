package ru.fixapp.fooproject.presentationlayer.resolution;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface NavigationResolver {
	void showFragment(BaseFragment fragment);

	void showRootFragment(BaseFragment fragment);

	void showFragmentWithoutBackStack(BaseFragment fragment);

	boolean onBackPressed();
}
