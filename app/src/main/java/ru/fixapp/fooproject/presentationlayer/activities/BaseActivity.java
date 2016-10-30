package ru.fixapp.fooproject.presentationlayer.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import javax.inject.Inject;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.utils.OnBackPressedListener;
public abstract class BaseActivity extends AppCompatActivity implements BaseActivityView {


	@Inject NavigationResolver navigationResolver;

	private static final int PERMANENT_FRAGMENTS = 1; // left menu, retain ,ect
	protected boolean doubleBackToExitPressedOnce;
	protected DrawerLayout drawerLayout;
	private ProgressWheel progress;
	private boolean inProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutResourceID());
		int drawerLayout = getDrawerLayout();
		if (drawerLayout != 0) {
			this.drawerLayout = (DrawerLayout) findViewById(drawerLayout);
		}

		configureToolBar();
		injectDagger();
		FragmentManager fm = getSupportFragmentManager();
		Fragment contentFragment = fm.findFragmentById(getContainerID());
		if (contentFragment == null) {
			navigationResolver.showRootFragment(createStartFragment());
			Fragment drawer = createDrawer();
			if (drawer != null) {
				fm.beginTransaction()
						.add(getDrawerContentFrame(), drawer)
						.commit();
			}
		}
		configureProgressBar();
	}

	protected abstract void injectDagger();

	@IdRes
	protected int getDrawerContentFrame() {
		return 0;
	}

	@IdRes
	protected int getDrawerLayout() {
		return 0;
	}

	protected abstract BaseFragment createDrawer();


	protected void configureToolBar() {

	}

	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main;
	}

	private void configureProgressBar() {
		progress = (ProgressWheel) findViewById(R.id.progress_wheel);
		progress.setOnTouchListener((v, event) -> true);
	}

	public void updateIcon() {
	}

	protected abstract BaseFragment createStartFragment();

	protected int getContainerID() {
		return R.id.content;
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

	@Override
	public void onBackPressed() {
		if (processBackFragment()) {
			if (!inProgress) {
				hideProgress();
				if (!navigationResolver.onBackPressed()) {
					exit();
				}
			}
			updateIcon();
		}
	}

	private boolean processBackFragment() {
		Fragment fragmentById = getSupportFragmentManager().findFragmentById(getContainerID());
		OnBackPressedListener listener = fragmentById instanceof OnBackPressedListener ? ((OnBackPressedListener) fragmentById) : null;
		return listener == null || !listener.onBackPressed();
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
	public void showProgress() {
		inProgress = true;
		progress.setVisibility(View.VISIBLE);
		hideKeyboard();
	}

	@Override
	public void hideProgress() {
		inProgress = false;
		if (progress != null) {
			progress.setVisibility(View.GONE);
		}
	}

	protected View getRootView() {
		return findViewById(android.R.id.content);
	}
}
