package ru.fixapp.fooproject.presentationlayer.fragments.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrvilkaman.core.R2;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Optional;
import butterknife.Unbinder;
import ru.fixapp.fooproject.di.IHasComponent;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.IToolbar;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment
		implements BaseView, BaseActivityView, OnBackPressedListener {

	private static final String PREVIOUS_FRAGMENT = "previousFragment";

	//// TODO: 07.11.16 dont use public
	@Inject public  UIResolver uiResolver;
	@Inject public ThrowableResolver throwableResolver;
	@Inject public NavigationResolver navigationResolver;
	@Inject public P relationPresenter;
	@Inject public IToolbar toolbar;
	@Inject public BaseActivityView activityView;

	@Nullable @BindView(R2.id.progress_wheel) View progressBar;
	private String previousFragment;
	private Unbinder bind;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daggerInject(getComponent(ActivityComponent.class));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		if (isWorkCall()) {
			bind = ButterKnife.bind(this, view);
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (previousFragment != null) {
			outState.putString(PREVIOUS_FRAGMENT, previousFragment);
		}
	}

	public abstract void daggerInject(ActivityComponent component);

	@Optional
	@OnTouch(R2.id.parent)
	boolean onTouchParent() {
		hideKeyboard();
		return false;
	}

	@Override
	public void onDestroyView() {
		if (bind != null) {
			bind.unbind();
		}
		P presenter = getPresenter();
		presenter.onViewDetached();
		presenter.setView(null);
		super.onDestroyView();
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

	@Override
	public P getPresenter() {
		return relationPresenter;
	}

	public IToolbar getToolbar() {
		return toolbar;
	}

	@Override
	public void showToast(@StringRes int text) {
		uiResolver.showToast(text);
	}

	@Override
	public void showToast(@StringRes int resId, Object... arg) {
		uiResolver.showToast(resId,arg);
	}

	@Override
	public void handleError(Throwable throwable) {
		throwableResolver.handleError(throwable);
	}

	@Override
	public void showMessage(@StringRes int text) {
		uiResolver.showMessage(text);
	}

	@Override
	public void showMessage(@StringRes int text, Runnable callback) {
		uiResolver.showMessage(text, callback);
	}

	@Override
	public void showMessage(@StringRes int resId, Object... arg) {
		uiResolver.showMessage(resId, arg);
	}

	@Override
	public void showSnackbar(@StringRes int textId) {
		uiResolver.showSnackbar(textId);
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@SuppressWarnings("unchecked")
	//// TODO: 07.11.16 must return protected...
	public  <T> T getComponent(Class<T> componentType) {
		return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
	}

	public NavigationResolver getNavigation() {
		return navigationResolver;
	}
}
