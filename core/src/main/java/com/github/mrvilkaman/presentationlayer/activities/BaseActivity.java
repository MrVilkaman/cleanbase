package com.github.mrvilkaman.presentationlayer.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.dev.LeakCanaryProxy;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.di.IHasComponent;
import com.github.mrvilkaman.presentationlayer.app.CoreApp;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.pnikosis.materialishprogress.ProgressWheel;

import javax.inject.Inject;

public abstract class BaseActivity<C extends ActivityCoreComponent> extends AppCompatActivity
		implements BaseActivityView, IHasComponent<C> {

	@Inject NavigationResolver navigationResolver;
	@Inject @Nullable ToolbarResolver toolbarResolver;
	@Inject @Nullable LeftDrawerHelper drawerHelper;

	private C activityComponent;

	private ProgressWheel progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutResourceID());
		injectDagger();
		View rootView = getRootView();
		if (drawerHelper != null) {
			drawerHelper.init(rootView);
		}
		if (toolbarResolver != null) {
			toolbarResolver.init(rootView, this);
		}
		navigationResolver.init();
		configureProgressBar();
	}

	protected int getActivityLayoutResourceID() {
		return R.layout.cleanbase_activity_content_with_toolbar;
	}

	private void configureProgressBar() {
		progress = (ProgressWheel) findViewById(R.id.progress_wheel);
		progress.setOnTouchListener((v, event) -> true);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (toolbarResolver != null) {
			toolbarResolver.onPrepareOptionsMenu(menu);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (toolbarResolver != null) {
			toolbarResolver.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	protected int getContainerID() {
		return R.id.content;
	}

	@Override
	public void onBackPressed() {
		navigationResolver.onBackPressed();
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

	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<T> componentType) {
		return componentType.cast(((IHasComponent<T>) CoreApp.get(this)).getComponent());
	}

	@Override
	public C getComponent() {
		injectDagger();
		return activityComponent;
	}

	private void injectDagger() {
		if (activityComponent != null) {
			return;
		}
		activityComponent = createComponent();
		injectMe(activityComponent);
	}

	protected abstract void injectMe(C activityComponent);

	protected abstract C createComponent();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LeakCanaryProxy leakCanaryProxy = activityComponent.provideLeakCanaryProxy();
		if (leakCanaryProxy != null) {
			leakCanaryProxy.init();
		}
	}
}
