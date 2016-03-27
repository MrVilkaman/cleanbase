package com.github.mrvilkaman.namegenerator.presentationlayer.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.github.mrvilkaman.namegenerator.R;
import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.namegenerator.presentationlayer.utils.IToolbar;
import com.github.mrvilkaman.namegenerator.presentationlayer.utils.OnBackPressedListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements BaseActivityPresenter, BaseActivityView {

	private static final int PERMANENT_FRAGMENTS = 0; // left menu, retain ,ect

	private BaseFragment nextFragment;

	private boolean backStack;
	private boolean isRoot;
	private boolean forceLoad;
	private ProgressWheel progress;
	private boolean inProgress;
	private boolean openIfCreated;
	protected boolean doubleBackToExitPressedOnce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutResourceID());

		launchApi();

		FragmentManager fm = getSupportFragmentManager();
		Fragment contentFragment = fm.findFragmentById(getContainerID());
		if (contentFragment == null) {
			loadRootFragment(createStartFragment(), true, true, false, false);
		}
		configureProgressBar();
	}

	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main;
	}

	protected abstract void launchApi();

	private void configureProgressBar() {
		progress = (ProgressWheel) findViewById(R.id.progress_wheel);
		progress.setOnTouchListener((v, event) -> true);
	}

	protected IToolbar.OnHomeClick getHomeButtonListener() {
		return () -> {
			if (hasChild()) {
				back();
				updateIcon();
			}
		};
	}

	protected void updateIcon() {
		IToolbar toolbar = getToolbar();
		if (toolbar != null) {
			if (hasChild()) {
				toolbar.showBackIcon();
			} else {
				toolbar.showHomeIcon();
			}
		}
	}

	protected abstract BaseFragment createStartFragment();

	protected int getContainerID() {
		return R.id.content;
	}

	@Override
	public void loadRootFragment(BaseFragment fragment, boolean addToBackStack, boolean isRoot, boolean forceLoad, boolean openIfcreated) {
		nextFragment = fragment;
		backStack = addToBackStack;
		this.isRoot = isRoot;
		this.forceLoad = forceLoad;
		this.openIfCreated = openIfcreated;
		nextFragment();
	}

	private void exit() {
		if (doubleBackToExitPressedOnce) {
			finish();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Еще раз", Toast.LENGTH_SHORT)
				.show();
		new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1000);
	}

	protected boolean hasChild() {
		return PERMANENT_FRAGMENTS < getSupportFragmentManager().getBackStackEntryCount();
	}

	public void onBackHandle() {
		if (!inProgress) {
			hideProgress();

			FragmentManager supportFragmentManager = getSupportFragmentManager();
			BaseFragment current = (BaseFragment) supportFragmentManager.findFragmentById(getContainerID());
			if (current != null && current.getPreviousFragment() != null) {
				supportFragmentManager.popBackStackImmediate(current.getPreviousFragment(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
				updateIcon();
			} else {
				exit();
			}
		}
	}

	protected void clearBackStackToFragment(String name) {
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		if (supportFragmentManager != null && name != null) {
			supportFragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	@Override
	public void onBackPressed() {
		if (processBackFragment()) {
			onBackHandle();
			updateIcon();
		}
	}

	private boolean processBackFragment() {
		Fragment fragmentById = getSupportFragmentManager().findFragmentById(getContainerID());
		OnBackPressedListener listener = fragmentById instanceof OnBackPressedListener ? ((OnBackPressedListener) fragmentById) : null;
		return listener == null || !listener.onBackPressed();
	}

	void nextFragment() {
		if (nextFragment != null) {
			BaseFragment currentFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(getContainerID());
			boolean hasOldFragment = currentFragment != null;
			boolean isAlreadyLoaded = false;
			if (hasOldFragment) {
				isAlreadyLoaded = currentFragment.getName()
						.equals(nextFragment.getName());
			}

			if (!(hasOldFragment && isAlreadyLoaded)) {
				IToolbar toolbar = getToolbar();
				if (isRoot) {
					clearBackStack();
					if (toolbar != null) {
						toolbar.showHomeIcon();
					}
				} else {
					if (toolbar != null) {
						toolbar.showBackIcon();
					}
					if (openIfCreated) {
					}
				}
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				boolean b = backStack || isRoot;
				fragmentTransaction.replace(getContainerID(), nextFragment, nextFragment.getName());
				if (currentFragment != null && !isRoot) {
					nextFragment.setPreviousFragment(b ? currentFragment.getName() : currentFragment.getPreviousFragment());
					fragmentTransaction.addToBackStack(currentFragment.getName());
				} else {
					nextFragment.setPreviousFragment(null);
					fragmentTransaction.addToBackStack(null);
				}
				fragmentTransaction.commit();
			}
			nextFragment = null;
		}
	}

	private void clearBackStack() {
		FragmentManager fragmentManager = getSupportFragmentManager();

		if (0 < fragmentManager.getBackStackEntryCount()) {
			int id = fragmentManager.getBackStackEntryAt(0)
					.getId();
			fragmentManager.popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		List<Fragment> fragments = fragmentManager.getFragments();
		if (fragments == null) {
			return;
		}
		FragmentTransaction trans = fragmentManager.beginTransaction();
		for (Fragment fragment : fragments) {
			if (fragment != null) {
				trans.remove(fragment);
			}
		}
		trans.commit();

	}

	@Override
	public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = getCurrentFocus();
		if (view != null) {
			view.clearFocus();
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	@Override
	public void back() {
		onBackPressed();
	}

	@Override
	public void showProgress() {
		inProgress = true;
		progress.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		inProgress = false;
		progress.setVisibility(View.GONE);
	}

	@Override
	public void clearProgress() {
		inProgress = false;
	}
}
