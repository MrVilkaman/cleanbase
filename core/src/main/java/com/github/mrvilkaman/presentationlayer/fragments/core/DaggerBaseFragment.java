package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.di.IHasComponent;
import com.github.mrvilkaman.di.INeedInject;

import rx.annotations.Experimental;

@Experimental
public abstract class DaggerBaseFragment<P extends BasePresenter, C> extends BaseFragment<P>
		implements INeedInject<C>, IHasComponent<C> {

	private C fragmentComponent;

	public C getComponent() {
		return fragmentComponent;
	}

	@Override
	public void daggerInject() {
		if (fragmentComponent != null) {
			return;
		}
		fragmentComponent = createComponent();
		injectMe(fragmentComponent);
	}

	protected abstract C createComponent();

	@SuppressWarnings("unchecked")

	@Override
	protected void attachCustomView(@NonNull BaseCustomView customWidget) {
		if (customWidget instanceof INeedInject) {
			((INeedInject) customWidget).injectMe(getComponent());
		}
		super.attachCustomView(customWidget);
	}

	protected void attachCustomView(@NonNull BaseCustomView customWidget, C component) {
		if (customWidget instanceof INeedInject) {
			((INeedInject) customWidget).injectMe(component);
		}
		super.attachCustomView(customWidget);
	}
}
