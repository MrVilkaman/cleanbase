package com.github.mrvilkaman.presentationlayer.fragments;


import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.BasePresenter;

import rx.annotations.Experimental;

@Experimental
public abstract class DaggerBaseFragment<P extends BasePresenter,C> extends BaseFragment<P> {

	private C fragmentComponent;

	public C getFragmentComponent() {
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

	protected abstract void injectMe(C activityComponent);

	protected abstract C createComponent();

}
