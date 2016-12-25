package ru.fixapp.fooproject.presentationlayer.resolution.toolbar;


import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.FragmentResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolver;

public class MyToolbarResolverCallback implements ToolbarResolver.ToolbarResolverCallback {
	private final FragmentResolver fragmentManager;
	private final LeftDrawerHelper drawerHelper;
	private final BaseActivityView activityView;
	private final ToolbarResolver toolbarResolver;
	private final NavigationResolver navigationResolver;

	public MyToolbarResolverCallback(FragmentResolver fragmentManager,
									 LeftDrawerHelper drawerHelper, BaseActivityView activityView,
									 ToolbarResolver toolbarResolver,
									 NavigationResolver navigationResolver) {
		this.fragmentManager = fragmentManager;
		this.drawerHelper = drawerHelper;
		this.activityView = activityView;
		this.toolbarResolver = toolbarResolver;
		this.navigationResolver = navigationResolver;
	}

	@Override
	public void onClickHome() {
		if (fragmentManager.isRootScreen()) {
			drawerHelper.open();
		} else {
			navigationResolver.onBackPressed();
			activityView.hideKeyboard();
		}
	}

	@Override
	public void updateIcon() {
		if (fragmentManager.isRootScreen()) {
			toolbarResolver.showHomeIcon();
		} else {
			toolbarResolver.showBackIcon();
		}
	}
}