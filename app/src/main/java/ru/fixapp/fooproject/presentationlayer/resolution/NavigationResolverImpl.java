package ru.fixapp.fooproject.presentationlayer.resolution;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import ru.fixapp.fooproject.presentationlayer.activities.ToolbarResolver;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;


public class NavigationResolverImpl implements NavigationResolver {

	private Activity currentActivity;
	private FragmentResolver fragmentManager;
	private LeftDrawerHelper drawerHelper;
	private ToolbarResolver toolbarResolver;


	public NavigationResolverImpl(Activity currentActivity, FragmentResolver fragmentManager,
								  LeftDrawerHelper drawerHelper, ToolbarResolver toolbarResolver) {
		this.currentActivity = currentActivity;
		this.fragmentManager = fragmentManager;
		this.drawerHelper = drawerHelper;
		this.toolbarResolver = toolbarResolver;

		fragmentManager.setCallback(new FragmentResolver.FragmentResolverCallback() {
			@Override
			public void onRootFragment() {
				toolbarResolver.showHomeIcon();
			}

			@Override
			public void onNotRootFragment() {
				toolbarResolver.showBackIcon();
			}
		});

		toolbarResolver.setCallback(new ToolbarResolver.ToolbarResolverCallback() {
			@Override
			public void onClickHome() {
				if (fragmentManager.isRootScreen()) {
					drawerHelper.open();
				} else {
					onBackPressed();
					//// TODO: 30.10.16 !!
//					hideKeyboard();
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
		});
	}

	@Override
	public boolean onBackPressed() {
		return fragmentManager.onBackPressed();
	}

	@Override
	public void showFragment(BaseFragment fragment) {
		if (drawerHelper.isOpen()) {
			drawerHelper.close(() -> {
				toolbarResolver.clear();
				fragmentManager.showFragment(fragment);
			});
		} else {
			fragmentManager.showFragment(fragment);
		}
	}

	@Override
	public void showRootFragment(BaseFragment fragment) {
		if (drawerHelper.isOpen()) {
			drawerHelper.close(() -> {
				toolbarResolver.clear();
				fragmentManager.showFragmentWithoutBackStack(fragment);
			});
		} else {
			fragmentManager.showFragmentWithoutBackStack(fragment);
		}
	}

	@Override
	public void showFragmentWithoutBackStack(BaseFragment fragment) {
		if (drawerHelper.isOpen()) {
			drawerHelper.close(() -> {
				toolbarResolver.clear();
				fragmentManager.showFragmentWithoutBackStack(fragment);
			});
		} else {
			fragmentManager.showFragmentWithoutBackStack(fragment);
		}
	}

	@Override
	public void openActivity(Class<? extends FragmentActivity> aClass) {
		currentActivity.startActivity(new Intent(currentActivity, aClass));
		currentActivity.finish();
	}

	@Override
	public void back() {
		if (drawerHelper.isOpen()) {
			drawerHelper.close();
		} else {
			onBackPressed();
			toolbarResolver.updateIcon();
		}
	}
}
