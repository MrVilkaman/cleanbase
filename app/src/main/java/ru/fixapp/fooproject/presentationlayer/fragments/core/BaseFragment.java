package ru.fixapp.fooproject.presentationlayer.fragments.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTouch;
import butterknife.Optional;
import butterknife.Unbinder;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityPresenter;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.app.App;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolverImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;
import ru.fixapp.fooproject.presentationlayer.utils.OnBackPressedListener;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView, BaseActivityView, OnBackPressedListener {

	private static final String PREVIOUS_FRAGMENT = "previousFragment";

	private String previousFragment;

	UIResolver uiResolver;
	ThrowableResolver throwableResolver;

	@Inject
	P relationPresenter;

	@Nullable
	@BindView(R.id.progress_wheel)
	View progressBar;
	private Unbinder bind;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daggerInject();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		if (isWorkCall()) {
			//// TODO: 17.09.16 it will be doing by dagger2
			uiResolver = createUiResolver(view);
			throwableResolver = createThrowableResolver(uiResolver);
			P presenter = getPresenter();
			presenter.setView(this);
			onCreateView(view, savedInstanceState);
			presenter.onViewAttached();
		}
		if (savedInstanceState != null) {
			previousFragment = savedInstanceState.getString(PREVIOUS_FRAGMENT, previousFragment);
		}
		return view;
	}

	//// TODO: 17.09.16 it will be doing by dagger2
	public UIResolver createUiResolver(View view) {
		return new UIResolverImpl(view);
	}
	//// TODO: 17.09.16 it will be doing by dagger2
	public ThrowableResolver createThrowableResolver(UIResolver ui) {
		return new ThrowableResolverImpl(ui);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (previousFragment != null) {
			outState.putString(PREVIOUS_FRAGMENT, previousFragment);
		}
	}

	public abstract void daggerInject();

	protected AppComponent getAppComponent() {
		return App.get(getActivity())
				.getAppComponent();
	}

	@Optional
	@OnTouch(R.id.parent)
	boolean onTouchParent() {
		hideKeyboard();
		return false;
	}

	@Override
	public void onDestroyView() {
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
	public void back() {
		getActivityView().back();
	}

	@Override
	public void hideKeyboard() {
		getActivityView().hideKeyboard();
	}

	@Override
	public void showProgress() {
		if (progressBar == null) {
			getActivityView().showProgress();
		} else {
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideProgress() {
		if (progressBar == null) {
			BaseActivityView activityView = getActivityView();
			if (activityView != null)
				activityView.hideProgress();
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void clearProgress() {
		getActivityView().clearProgress();
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

	protected void showFragment(BaseFragment fragment) {
		getBaseActivity().loadRootFragment(fragment, true, false, false, false);
	}

	protected void showRootFragment(BaseFragment fragment) {
		getBaseActivity().loadRootFragment(fragment, false, true, false, false);
	}

	protected void showFragmentWithoutBackStack(BaseFragment fragment) {
		getBaseActivity().loadRootFragment(fragment, false, false, false, false);
	}

	protected void showOrOpenFragment(BaseFragment fragment) {
		getBaseActivity().loadRootFragment(fragment, true, false, false, true);
	}

	public BaseActivityPresenter getBaseActivity() {
		return (BaseActivityPresenter) getActivity();
	}

	public BaseActivityView getActivityView() {
		return (BaseActivityView) getActivity();
	}

	@Override
	public P getPresenter() {
		return relationPresenter;
	}

	@Override
	public IToolbar getToolbar() {
		return getActivityView().getToolbar();
	}

	@Override
	public void showToast(@StringRes int text) {
		uiResolver.showToast(text);
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
		uiResolver.showMessage(text,callback);
	}

	@Override
	public void showMessage(@StringRes int resId, Object... arg) {
		uiResolver.showMessage(resId,arg);
	}

	@Override
	public void showSnackbar(@StringRes int textId) {
		uiResolver.showSnackbar(textId);
	}

	public String getName() {
		return getClass().getSimpleName();
	}

}
