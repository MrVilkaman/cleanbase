package ru.fixapp.fooproject.presentationlayer.resolution;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import ru.fixapp.fooproject.presentationlayer.activities.ISingletonFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public class FragmentResolverImpl implements FragmentResolver {


	private FragmentResolverCallback callback;
	private FragmentManager fragmentManager;
	private int containerID;

	private BaseFragment nextFragment;
	private boolean backStack;
	private boolean isRoot;

	public FragmentResolverImpl(FragmentManager fragmentManager, int containerID) {
		this.fragmentManager = fragmentManager;
		this.containerID = containerID;
	}

	@Override
	public boolean onBackPressed() {
		BaseFragment current = (BaseFragment) fragmentManager.findFragmentById(containerID);
		if (current != null && current.getPreviousFragment() != null) {
			fragmentManager.popBackStackImmediate(current.getPreviousFragment(),
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			return true;
		} else {
			return false;
		}
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
				if (isRoot) {
					clearBackStack();
					if(callback != null)
						callback.onRootFragment();
				} else {
					if(callback != null) {
						if (isRootScreen() && !backStack) {
							callback.onRootFragment();
						}else{
							callback.onNotRootFragment();
						}
					}
				}
			}
			//// TODO: 30.10.16 maybe need?
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

	@Override
	public void back() {
		onBackPressed();
	}

	@Override
	public void setCallback(FragmentResolverCallback callback) {
		this.callback = callback;
	}

	private static final int PERMANENT_FRAGMENTS = 1; // left menu, retain ,ect

	@Override
	public boolean isRootScreen() {
		BaseFragment current = (BaseFragment) fragmentManager.findFragmentById(containerID);
		boolean b = current != null && current.getPreviousFragment() != null;

		boolean b1 = PERMANENT_FRAGMENTS < fragmentManager.getBackStackEntryCount();
		return !b1 || !b;
	}
}
