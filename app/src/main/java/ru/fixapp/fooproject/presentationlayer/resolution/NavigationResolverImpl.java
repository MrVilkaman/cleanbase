package ru.fixapp.fooproject.presentationlayer.resolution;

import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityPresenter;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;


public class NavigationResolverImpl implements NavigationResolver {

	private BaseActivityPresenter baseActivity;

	public NavigationResolverImpl(BaseActivityPresenter baseActivity) {
		this.baseActivity = baseActivity;
	}

	@Override
	public void showFragment(BaseFragment fragment) {
		baseActivity.loadRootFragment(fragment, true, false, false, false);
	}

	@Override
	public void showRootFragment(BaseFragment fragment) {
		baseActivity.loadRootFragment(fragment, false, true, false, false);
	}

	@Override
	public void showFragmentWithoutBackStack(BaseFragment fragment) {
		baseActivity.loadRootFragment(fragment, false, false, false, false);
	}

}
