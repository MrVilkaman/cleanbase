package com.github.mrvilkaman.presentationlayer.resolution.toolbar;


import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;

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