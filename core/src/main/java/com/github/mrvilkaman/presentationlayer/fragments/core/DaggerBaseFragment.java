package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.annotation.NonNull;

import com.github.mrvilkaman.di.IHasComponent;
import com.github.mrvilkaman.di.INeedInject;

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


	@Override
	protected void attachCustomView(@NonNull BaseCustomView customWidget) {
		attachCustomView(customWidget,getComponent());
	}

	@SuppressWarnings("unchecked")
	protected void attachCustomView(@NonNull BaseCustomView customWidget, Object component) {
		if (customWidget instanceof INeedInject) {
			((INeedInject) customWidget).injectMe(component);
		}
		super.attachCustomView(customWidget);
	}
}
