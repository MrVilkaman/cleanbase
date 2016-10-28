package ru.fixapp.fooproject.presentationlayer.resolution;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import ru.fixapp.fooproject.presentationlayer.activities.ISingletonFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;


public class NavigationResolverImpl implements NavigationResolver {

	private FragmentManager fragmentManager;
	private int containerID;


	private BaseFragment nextFragment;
	private boolean backStack;
	private boolean isRoot;

	public NavigationResolverImpl(FragmentManager fragmentManager, int containerID) {
		this.fragmentManager = fragmentManager;
		this.containerID = containerID;
	}

	@Override
	public void showFragment(BaseFragment fragment) {
		loadRootFragment(fragment, true, false);
	}

	@Override
	public void showRootFragment(BaseFragment fragment) {
		loadRootFragment(fragment, false, true);
	}

	@Override
	public void showFragmentWithoutBackStack(BaseFragment fragment) {
		loadRootFragment(fragment, false, false);
	}


	public void loadRootFragment(BaseFragment fragment, boolean addToBackStack, boolean isRoot) {
		nextFragment = fragment;
		backStack = addToBackStack;
		this.isRoot = isRoot;
		nextFragment();
	}


	void nextFragment() {
		if (nextFragment != null) {
			BaseFragment currentFragment =
					(BaseFragment) fragmentManager.findFragmentById(containerID);
			boolean hasOldFragment = currentFragment != null;
			boolean isAlreadyLoaded = false;
			if (hasOldFragment) {
				isAlreadyLoaded = currentFragment.getName().equals(nextFragment.getName());
			}

			if (!(hasOldFragment && isAlreadyLoaded)) {
				//				IToolbar toolbar = getToolbar();

				if (isRoot) {
					clearBackStack();
					//					if (toolbar != null) {
					//						toolbar.showHomeIcon();
					//					}
				} else {
					//					if (toolbar != null) {
					//						toolbar.showBackIcon();
				}
				//					if (openIfCreated) {
				//					}
			}
//			preCheckFragment(nextFragment.getName());
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			boolean b = backStack || isRoot;
			fragmentTransaction.replace(containerID, nextFragment, nextFragment.getName());
			if (currentFragment != null && !isRoot) {
				nextFragment.setPreviousFragment(
						b ? currentFragment.getName() : currentFragment.getPreviousFragment());
				fragmentTransaction.addToBackStack(currentFragment.getName());
			} else {
				nextFragment.setPreviousFragment(null);
				fragmentTransaction.addToBackStack(null);
			}
			fragmentTransaction.commit();

		}
		nextFragment = null;
	}


	private void clearBackStack() {

		if (0 < fragmentManager.getBackStackEntryCount()) {
			int id = fragmentManager.getBackStackEntryAt(0).getId();
			fragmentManager.popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		List<Fragment> fragments = fragmentManager.getFragments();
		if (fragments == null) {
			return;
		}
		FragmentTransaction trans = fragmentManager.beginTransaction();
		for (Fragment fragment : fragments) {
			if (fragment != null && !(fragment instanceof ISingletonFragment)) {
				trans.remove(fragment);
			}
		}
		trans.commit();

	}


}
