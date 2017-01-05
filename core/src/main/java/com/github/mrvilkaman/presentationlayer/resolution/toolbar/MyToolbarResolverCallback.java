package com.github.mrvilkaman.presentationlayer.resolution.toolbar;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;

public class MyToolbarResolverCallback implements ToolbarResolver.ToolbarResolverCallback {
	private final FragmentResolver fragmentManager;
	private final BaseActivityView activityView;
	private final @Nullable LeftDrawerHelper drawerHelper;
	private final @Nullable ToolbarResolver toolbarResolver;
	private final NavigationResolver navigationResolver;

	public MyToolbarResolverCallback(FragmentResolver fragmentManager,
									 @Nullable LeftDrawerHelper drawerHelper,
									 BaseActivityView activityView,
									 @Nullable ToolbarResolver toolbarResolver,
									 NavigationResolver navigationResolver) {
		this.fragmentManager = fragmentManager;
		this.drawerHelper = drawerHelper;
		this.activityView = activityView;
		this.toolbarResolver = toolbarResolver;
		this.navigationResolver = navigationResolver;
	}

	@Override
	public void onClickHome() {
		if (needShowHome()) {
			if (drawerHelper != null) {
				drawerHelper.open();
			}
		} else {
			navigationResolver.onBackPressed();
			activityView.hideKeyboard();
		}
	}

	@Override
	public void updateIcon() {
		if (toolbarResolver == null) {
			return;
		}

		if (needShowHome()) {
			toolbarResolver.showHomeIcon();
		} else {
			toolbarResolver.showBackIcon();
		}
	}

	private boolean needShowHome() {
		return fragmentManager.isRootScreen() && activityView.isTaskRoot();
	}
}