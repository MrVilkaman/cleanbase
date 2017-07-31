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
import com.github.mrvilkaman.di.INeedInject;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseCustomView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.IProgressState;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@SuppressWarnings("unchecked")
public abstract class BaseActivity<C extends ActivityCoreComponent, P extends BasePresenter>
		extends AppCompatActivity implements BaseActivityView, BaseView, IHasComponent<C>,
		INeedInject<C>,IProgressState {

	private List<BasePresenter> presenters = new ArrayList<>(1);

	@Nullable protected P presenter;
	@Inject @Nullable protected ToolbarResolver toolbarResolver;
	@Inject NavigationResolver navigationResolver;
	@Inject @Nullable LeftDrawerHelper drawerHelper;

	private C activityComponent;

	private @Nullable ProgressWheel progress;
	private InputMethodManager inputMethodManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutResourceID());
		injectDagger();
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		View rootView = getRootView();
		if (drawerHelper != null) {
			drawerHelper.init(rootView);
		}
		if (toolbarResolver != null) {
			toolbarResolver.init(rootView, this);
		}
		navigationResolver.init();
		configureProgressBar();
		if (presenter != null) {
			attachPresenter(presenter);
		}
		afterOnCreate();
	}

	public P getPresenter() {
		return presenter;
	}

	public void setPresenter(@Nullable P presenter) {
		this.presenter = presenter;
	}

	protected abstract void afterOnCreate();

	protected int getActivityLayoutResourceID() {
		return R.layout.cleanbase_activity_content_with_toolbar;
	}

	private void configureProgressBar() {
		progress = (ProgressWheel) findViewById(R.id.progress_wheel);
		if (progress != null) {
			progress.setOnTouchListener((v, event) -> true);
		}
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
		View view = getCurrentFocus();
		if (view != null) {
			view.clearFocus();
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	@Override
	public void showProgress() {
		if (progress != null) {
			progress.setVisibility(View.VISIBLE);
		}
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
		return DevUtils.getComponent(getApplicationContext(), componentType);
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

	protected abstract C createComponent();

	@Override
	protected void onDestroy() {
		if (presenter != null) {
			presenter.onViewDetached();
			//noinspection unchecked
			presenter.setView(null);
			detachPresenters();
		}
		super.onDestroy();
		LeakCanaryProxy leakCanaryProxy = activityComponent.provideLeakCanaryProxy();
		if (leakCanaryProxy != null) {
			leakCanaryProxy.watch(this);
		}
	}

	protected void attachPresenter(BasePresenter presenter) {
		presenter.setView(this);
		presenter.onViewAttached();
		presenters.add(presenter);
	}

	private void detachPresenters() {
		for (int i = presenters.size() - 1; i >= 0; i--) {
			detachPresenter(presenters.get(i));
			presenters.remove(i);
		}
	}

	protected void detachPresenter(BasePresenter presenter) {
		presenter.onViewDetached();
		presenter.setView(null);
	}

	protected void attachCustomView(BaseCustomView customWidget) {

		if (customWidget instanceof INeedInject) {
			((INeedInject) customWidget).injectMe(getComponent());
		}
		customWidget.bind(this);
		BasePresenter presenter = customWidget.getPresenter();
		if (presenter != null) {
			presenter.onViewAttached();
			presenters.add(presenter);
		}
	}
}
