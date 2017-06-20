package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.dev.LeakCanaryProxy;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("unchecked")
public abstract class BaseFragment<P extends BasePresenter> extends Fragment
		implements IBaseScreen {

	private static final String PREVIOUS_FRAGMENT = "previousFragment";

	//// TODO: 07.11.16 dont use public
	@Inject public UIResolver uiResolver;
	@Inject public ThrowableResolver throwableResolver;
	@Inject public NavigationResolver navigationResolver;
	@Inject public P relationPresenter;
	@Inject @Nullable public IToolbar toolbar;
	@Inject public BaseActivityView activityView;
	View progressBar;
	private List<BasePresenter> presenters = new ArrayList<>(1);
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
			attachPresenter(presenter);
			onCreateView(view, savedInstanceState);
		}
		if (savedInstanceState != null) {
			previousFragment = savedInstanceState.getString(PREVIOUS_FRAGMENT, previousFragment);
		}
		return view;
	}

	protected void attachPresenter(BasePresenter presenter) {
		presenter.setView(this);
		presenter.onViewAttached();
		presenters.add(presenter);
	}

	private void initView(View view) {
		progressBar = view.findViewById(R.id.progress_wheel);
		if (progressBar != null) {
			progressBar.setOnTouchListener((view1, motionEvent) -> {
				hideKeyboard();
				return false;
			});
		}

		View parent = view.findViewById(R.id.parent);
		if (parent != null) {
			parent.setOnTouchListener((v, event) -> {
				hideKeyboard();
				return true;
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
		detachPresenters();
		super.onDestroyView();
		LeakCanaryProxy leakCanaryProxy =
				DevUtils.getComponent(getActivity(),ActivityCoreComponent.class).provideLeakCanaryProxy();
		if (leakCanaryProxy != null) {
			leakCanaryProxy.watch(this);
		}
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

	@Override
	public boolean onBackPressed() {
		return false;
	}

	//	@Override
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

	@Override
	public String getPreviousFragment() {
		return previousFragment;
	}

	@Override
	public void setPreviousFragment(String previousFragment) {
		this.previousFragment = previousFragment;
	}

	protected abstract void onCreateView(View view, Bundle savedInstanceState);

	protected abstract int getLayoutId();

	public P getPresenter() {
		return relationPresenter;
	}

	@Nullable
	public IToolbar getToolbar() {
		return toolbar;
	}

	@Override
	public void handleError(Throwable throwable) {
		throwableResolver.handleError(throwable);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<T> componentType) {
		return DevUtils.getComponent(getActivity(),componentType);
	}

	public UIResolver getUiResolver() {
		return uiResolver;
	}

	public NavigationResolver getNavigation() {
		return navigationResolver;
	}

	protected void attachCustomView(@NonNull BaseCustomView customWidget) {
		customWidget.bind(this);
		BasePresenter presenter = customWidget.getPresenter();
		if (presenter != null) {
			presenter.onViewAttached();
			presenters.add(presenter);
		}
	}
}
