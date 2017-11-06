package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.MembersInjector;
import dagger.android.support.AndroidSupportInjection;


@SuppressWarnings("unchecked")
public abstract class BaseFragment<P extends BasePresenter> extends Fragment
		implements IBaseScreen, IProgressState {

	private static final String PREVIOUS_FRAGMENT = "previousFragment";

	//// TODO: 07.11.16 dont use public
	@Inject Lazy<UIResolver> uiResolver;
	@Inject P relationPresenter;
	@Inject @Nullable Lazy<IToolbar> toolbar;
	View progressBar;
	private List<BasePresenter> presenters = new ArrayList<>(1);
	private String previousFragment;

	@Override
	public void onAttach(Context context) {
		AndroidSupportInjection.inject(this);
		super.onAttach(context);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		if (savedInstanceState != null) {
			previousFragment = savedInstanceState.getString(PREVIOUS_FRAGMENT, previousFragment);
		}
		return view;
	}

	@Override
	@CallSuper
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		initView(view);
		P presenter = getPresenter();
		attachPresenter(presenter);
		onCreateView(view, savedInstanceState);
	}

	protected void attachPresenter(@NonNull BasePresenter presenter) {
		presenter.setView(this);
		presenter.onViewAttached();
		presenters.add(presenter);
	}

	protected void initView(View view) {
		progressBar = view.findViewById(R.id.progress_wheel);
		if (progressBar != null) {
			progressBar.setOnTouchListener(new MyOnTouchListener());
		}

		View parent = view.findViewById(R.id.parent);
		if (parent != null) {
			parent.setOnTouchListener(new MyOnTouchListener());
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (previousFragment != null) {
			outState.putString(PREVIOUS_FRAGMENT, previousFragment);
		}
	}

	@Override
	public void onDestroyView() {
		detachPresenters();
		super.onDestroyView();
//		LeakCanaryProxy leakCanaryProxy =
//				DevUtils.getComponent(getActivity(), ActivityCoreComponent.class)
//						.provideLeakCanaryProxy();
//		if (leakCanaryProxy != null) {
//			leakCanaryProxy.watch(this);
//		}
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
		DevUtils.hideKeyboard(getActivity());
	}

	@Override
	public void showProgress() {
		if (progressBar == null) {
			IProgressState parentProgressState = (IProgressState) getActivity();
			if (parentProgressState != null)
				parentProgressState.showProgress();
		} else {
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideProgress() {
		if (progressBar == null) {
			IProgressState parentProgressState = (IProgressState) getActivity();
			if (parentProgressState != null)
				parentProgressState.hideProgress();
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

	@Deprecated
	protected void onCreateView(View view, @Nullable Bundle savedInstanceState) {

	}

	protected abstract int getLayoutId();

	public P getPresenter() {
		return relationPresenter;
	}

	@Nullable
	public IToolbar getToolbar() {
		return toolbar != null ? toolbar.get() : null;
	}


	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<T> componentType) {
		return DevUtils.getComponent(getActivity(), componentType);
	}

	public UIResolver getUiResolver() {
		return uiResolver.get();
	}


	protected void attachCustomView(@NonNull BaseCustomView customWidget) {
		attachCustomView(customWidget, null);
	}

	protected <T extends BaseCustomView> void attachCustomView(@NonNull T customWidget,
	                                @Nullable MembersInjector<T> injector) {
		if (injector != null) {
			injector.injectMembers(customWidget);
		}
		customWidget.bind(this);
		BasePresenter presenter = customWidget.getPresenter();
		if (presenter != null) {
			presenter.onViewAttached();
			presenters.add(presenter);
		}
	}

	private class MyOnTouchListener implements View.OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			hideKeyboard();
			return false;
		}
	}
}
