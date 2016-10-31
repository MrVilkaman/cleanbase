package ru.fixapp.fooproject.presentationlayer.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pnikosis.materialishprogress.ProgressWheel;

import javax.inject.Inject;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.resolution.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.toolbar.MyToolbarImpl;
import ru.fixapp.fooproject.presentationlayer.utils.OnBackPressedListener;

public abstract class BaseActivity extends AppCompatActivity
		implements BaseActivityView, MyToolbarImpl.ToolbarCallbacks {



	@Inject NavigationResolver navigationResolver;
	@Inject ToolbarResolver toolbarResolver;
	@Inject LeftDrawerHelperImpl drawerHelper;

	private ProgressWheel progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutResourceID());


		injectDagger();
		configureProgressBar();
	}

	protected abstract void injectDagger();


	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main;
	}

	private void configureProgressBar() {
		progress = (ProgressWheel) findViewById(R.id.progress_wheel);
		progress.setOnTouchListener((v, event) -> true);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		toolbarResolver.onPrepareOptionsMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		toolbarResolver.onOptionsItemSelected(item);
		return super.onOptionsItemSelected(item);
	}


	public void updateIcon() {
		toolbarResolver.updateIcon();
	}

	protected int getContainerID() {
		return R.id.content;
	}

	@Override
	public void onBackPressed() {
		if (processBackFragment()) {
			hideProgress();
			navigationResolver.onBackPressed();
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
