package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.dev.LeakCanaryProxy;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.di.IHasComponent;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.exceptions.Exceptions;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment
		implements BaseView, BaseActivityView, OnBackPressedListener {

	private static final String PREVIOUS_FRAGMENT = "previousFragment";

	//// TODO: 07.11.16 dont use public
	@Inject public UIResolver uiResolver;
	@Inject public ThrowableResolver throwableResolver;
	@Inject public NavigationResolver navigationResolver;
	@Inject public P relationPresenter;
	@Inject @Nullable public IToolbar toolbar;
	@Inject public BaseActivityView activityView;

	View progressBar;
	private String previousFragment;
	private Unbinder bind;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daggerInject();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		if (isWorkCall()) {
			bind = ButterKnife.bind(this, view);
			initView(view);
			P presenter = getPresenter();
			presenter.setView(this);
			presenter.onViewAttached();
			onCreateView(view, savedInstanceState);
		}
		if (savedInstanceState != null) {
			previousFragment = savedInstanceState.getString(PREVIOUS_FRAGMENT, previousFragment);
		}
		return view;
	}

	private void initView(View view) {
		progressBar = view.findViewById(R.id.progress_wheel);
		if (progressBar != null) {
			progressBar.setOnTouchListener((view1, motionEvent) -> {
				hideKeyboard();
				return false;
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (previousFragment != null) {
			outState.putString(PREVIOUS_FRAGMENT, previousFragment);
		}
	}

	public abstract void daggerInject();

	@Override
	public void onDestroyView() {
		if (bind != null) {
			bind.unbind();
		}
		P presenter = getPresenter();
		presenter.onViewDetached();
		presenter.setView(null);
		super.onDestroyView();
		LeakCanaryProxy leakCanaryProxy =
				getComponent(ActivityCoreComponent.class).provideLeakCanaryProxy();
		if (leakCanaryProxy != null) {
			leakCanaryProxy.init();
		}
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	@Override
	public void hideKeyboard() {
		activityView.hideKeyboard();
	}

	@Override
	public void showProgress() {
		if (progressBar == null) {
			activityView.showProgress();
		} else {
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideProgress() {
		if (progressBar == null) {
			activityView.hideProgress();
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

	private boolean isWorkCall() {
		return true;
	}

	public String getPreviousFragment() {
		return previousFragment;
	}

	public void setPreviousFragment(String previousFragment) {
		this.previousFragment = previousFragment;
	}

	protected abstract void onCreateView(View view, Bundle savedInstanceState);

	protected abstract int getLayoutId();

	public P getPresenter() {
		return relationPresenter;
	}

	public IToolbar getToolbar() {
		return toolbar;
	}

	@Override
	public void handleError(Throwable throwable) {
		throwableResolver.handleError(throwable);
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@SuppressWarnings("unchecked")
	//// TODO: 07.11.16 must return protected...
	public <T> T getComponent(Class<T> componentType) {
		return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
	}

	public UIResolver getUiResolver() {
		return uiResolver;
	}

	public NavigationResolver getNavigation() {
		return navigationResolver;
	}

	@Override
	public boolean isTaskRoot() {
		throw Exceptions.propagate(new NoSuchMethodException("Plaese dont use this method in fragment!"));
	}
}
