package ru.fixapp.fooproject.presentationlayer.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.toolbar.MyToolbarImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;
import ru.fixapp.fooproject.presentationlayer.utils.OnBackPressedListener;

public abstract class BaseActivity extends AppCompatActivity
		implements BaseActivityView, MyToolbarImpl.ToolbarCallbacks {


	private static final int PERMANENT_FRAGMENTS = 1; // left menu, retain ,ect
	protected boolean doubleBackToExitPressedOnce;

	@Inject NavigationResolver navigationResolver;
	@Inject ToolbarMenuHelper toolbarMenuHelper;
	@Inject LeftDrawerHelper drawerHelper;

	private ProgressWheel progress;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutResourceID());


		injectDagger();
		configureToolBar();
		FragmentManager fm = getSupportFragmentManager();
		Fragment contentFragment = fm.findFragmentById(getContainerID());
		if (contentFragment == null) {
			navigationResolver.showRootFragment(createStartFragment());

			//// TODO: 30.10.16 !!
			Fragment drawer = createDrawer();
			if (drawer != null) {
				fm.beginTransaction().add(drawerHelper.getDrawerContentFrame(), drawer).commit();
			}
		}
		configureProgressBar();
	}

	protected abstract void injectDagger();


	protected abstract BaseFragment createDrawer();

	protected void configureToolBar() {
		toolbar = ButterKnife.findById(this, R.id.toolbar_actionbar);
		setSupportActionBar(toolbar);
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar == null)
			return;
		supportActionBar.setHomeButtonEnabled(true);
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(v -> {
			if (hasChild()) {
				onBackPressed();
			} else {
				hideKeyboard();
				drawerHelper.close();
			}
		});

	}

	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main;
	}

	private void configureProgressBar() {
		progress = (ProgressWheel) findViewById(R.id.progress_wheel);
		progress.setOnTouchListener((v, event) -> true);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		toolbarMenuHelper.onPrepareOptionsMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		toolbarMenuHelper.onOptionsItemSelected(item);
		return super.onOptionsItemSelected(item);
	}


	public void updateIcon() {
		ActionBar supportActionBar = getSupportActionBar();
		if (toolbar != null && supportActionBar != null) {
			if (hasChild()) {
				supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back);
			} else {
				supportActionBar.setHomeAsUpIndicator(R.drawable.ic_home);
			}
		}
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
		Toast.makeText(this, "Еще раз", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1000);
	}

	protected boolean hasChild() {
		return PERMANENT_FRAGMENTS < getSupportFragmentManager().getBackStackEntryCount();
	}

	@Override
	public void onBackPressed() {
		if (processBackFragment()) {
			hideProgress();
			if (!navigationResolver.onBackPressed()) {
				exit();
			}
			updateIcon();
		}
	}

	private boolean processBackFragment() {
		Fragment fragmentById = getSupportFragmentManager().findFragmentById(getContainerID());
		OnBackPressedListener listener = fragmentById instanceof OnBackPressedListener ?
				((OnBackPressedListener) fragmentById) : null;
		return listener == null || !listener.onBackPressed();
	}


	@Override
	public void hideKeyboard() {
		InputMethodManager imm =
				(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = getCurrentFocus();
		if (view != null) {
			view.clearFocus();
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	@Override
	public void showProgress() {
		progress.setVisibility(View.VISIBLE);
		hideKeyboard();
	}

	@Override
	public void hideProgress() {
		if (progress != null) {
			progress.setVisibility(View.GONE);
		}
	}

	protected View getRootView() {
		return findViewById(android.R.id.content);
	}

}
