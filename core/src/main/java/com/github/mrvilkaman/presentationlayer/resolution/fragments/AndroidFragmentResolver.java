package com.github.mrvilkaman.presentationlayer.resolution.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.ISingletonFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.OnBackPressedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AndroidFragmentResolver implements FragmentResolver {

	private static final int EMTPY_CODE = -1;
	private FragmentResolverCallback callback;
	private FragmentManager fragmentManager;
	private int containerID;
	private BaseFragment nextFragment;
	private boolean backStack;
	private boolean isRoot;
	private int code = EMTPY_CODE;

	public AndroidFragmentResolver(FragmentManager fragmentManager, int containerID) {
		this.fragmentManager = fragmentManager;
		this.containerID = containerID;
	}

	@Override
	public boolean processBackFragment() {
		Fragment fragmentById = getCurrentFragment();
		OnBackPressedListener listener = fragmentById instanceof OnBackPressedListener ?
				((OnBackPressedListener) fragmentById) : null;
		return listener == null || !listener.onBackPressed();
	}

	@Override
	public boolean checkBackStack() {
		BaseFragment current = (BaseFragment) getCurrentFragment();
		return current != null && current.getPreviousFragment() != null;
	}

	@Override
	public void popBackStack() {
		BaseFragment current = (BaseFragment) getCurrentFragment();
		fragmentManager.popBackStackImmediate(current.getPreviousFragment(),
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
		if (nextFragment != null) {
			BaseFragment currentFragment = (BaseFragment) getCurrentFragment();
			boolean hasOldFragment = currentFragment != null;
			boolean isAlreadyLoaded = false;
			if (hasOldFragment) {
				isAlreadyLoaded = currentFragment.getName()
						.equals(nextFragment.getName());
			}

			if (!(hasOldFragment && isAlreadyLoaded)) {
				updateToolbar();
			}

			if (code != EMTPY_CODE) {
				nextFragment.setTargetFragment(currentFragment, code);
				code = EMTPY_CODE;
			}
			doTransaction(currentFragment);
		}
		nextFragment = null;
	}

	public void updateToolbar() {
		if (this.isRoot) {
			clearBackStack();
			if (callback != null)
				callback.onRootFragment();
		} else {
			if (callback != null) {
				if (isRootScreen() && !backStack) {
					callback.onRootFragment();
				} else {
					callback.onNotRootFragment();
				}
			}
		}
	}

	void doTransaction(BaseFragment currentFragment) {
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		boolean b = backStack || this.isRoot;
		fragmentTransaction.replace(containerID, nextFragment, nextFragment.getName());
		if (currentFragment != null && !this.isRoot) {
			addChildFragment(currentFragment, fragmentTransaction,
					b ? currentFragment.getName() : currentFragment.getPreviousFragment());
		} else {
			addRootFragment(fragmentTransaction);
		}
		fragmentTransaction.commit();
	}

	void addChildFragment(BaseFragment currentFragment, FragmentTransaction fragmentTransaction,
						  String previousFragment) {
		nextFragment.setPreviousFragment(previousFragment);
		fragmentTransaction.addToBackStack(currentFragment.getName());
	}

	void addRootFragment(FragmentTransaction fragmentTransaction) {
		if (callback != null)
			callback.onRootFragment();
		nextFragment.setPreviousFragment(null);
		fragmentTransaction.addToBackStack(null);
	}

	@Override
	public void setTargetFragmentCode(int code) {
		this.code = code;
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		Fragment currentFragment = getCurrentFragment();
		if (currentFragment != null) {
			currentFragment.startActivityForResult(intent, requestCode);
		}
	}

	private Fragment getCurrentFragment() {
		return fragmentManager.findFragmentById(containerID);
	}

	void clearBackStack() {

		if (0 < fragmentManager.getBackStackEntryCount()) {
			int id = fragmentManager.getBackStackEntryAt(0)
					.getId();
			fragmentManager.popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		List<Fragment> fragments = getFragments();
		FragmentTransaction trans = fragmentManager.beginTransaction();
		for (Fragment fragment : fragments) {
			trans.remove(fragment);
		}
		trans.commit();
	}

	private List<Fragment> getFragments() {
		List<Fragment> fragments = fragmentManager.getFragments();
		if (fragments == null) {
			return Collections.emptyList();
		}
		fragments = new ArrayList<>(fragments);
		for (int i = fragments.size() - 1; 0 <= i; i--) {
			Fragment fragment = fragments.get(i);
			if (!isSingletonFragment(fragment)) {
				fragments.remove(i);
			}
		}
		return fragments;
	}

	private boolean isSingletonFragment(Fragment fragment) {
		return fragment != null && !(fragment instanceof ISingletonFragment);
	}

	@Override
	public void setCallback(FragmentResolverCallback callback) {
		this.callback = callback;
	}

	@Override
	public boolean isRootScreen() {
		BaseFragment current = (BaseFragment) getCurrentFragment();
		boolean b = current != null && current.getPreviousFragment() != null;

		boolean b1 = getFragments().isEmpty();
		return b1 || !b;
	}

	@Override
	public boolean hasFragment() {
		Fragment contentFragment = getCurrentFragment();
		return contentFragment != null;
	}

	@Override
	public void addStaticFragment(int contentId, BaseFragment fragment) {
		if (!(fragment instanceof ISingletonFragment)) {
			throw new IllegalArgumentException("fragment must impliment ISingletonFragment");
		}

		fragmentManager.beginTransaction()
				.add(contentId, fragment)
				.commit();
	}
}
