package ru.fixapp.fooproject.presentationlayer.resolution;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.TimeUnit;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.photomaker.PhotoMakerScreenFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarResolver;

import static rx.Observable.just;


public class NavigationResolverImpl implements NavigationResolver {

	protected boolean doubleBackToExitPressedOnce;
	private Activity currentActivity;
	private FragmentResolver fragmentManager;
	private LeftDrawerHelper drawerHelper;
	private ToolbarResolver toolbarResolver;
	private UIResolver uiResolver;
	private BaseActivityView activityView;

	public NavigationResolverImpl(Activity currentActivity, FragmentResolver fragmentManager,
								  LeftDrawerHelper drawerHelper, ToolbarResolver toolbarResolver,
								  UIResolver uiResolver, BaseActivityView activityView) {
		this.currentActivity = currentActivity;
		this.fragmentManager = fragmentManager;
		this.drawerHelper = drawerHelper;
		this.toolbarResolver = toolbarResolver;
		this.uiResolver = uiResolver;
		this.activityView = activityView;

		init();
	}

	@Override
	public void init() {
		toolbarResolver.setCallback(
				new MyToolbarResolverCallback(fragmentManager, drawerHelper, activityView,
						toolbarResolver));


		if (!fragmentManager.hasFragment()) {
			fragmentManager.showRootFragment(createStartFragment());

			if (drawerHelper.hasDrawer()) {
				fragmentManager.addDrawer(drawerHelper.getDrawerContentFrame(),
						drawerHelper.getDrawerFragment());
			}
		}
	}

	@Override
	public BaseFragment createStartFragment() {
		return PhotoMakerScreenFragment.open();
	}

	@Override
	public void onBackPressed() {
		if (fragmentManager.processBackFragment()) {
			activityView.hideProgress();
			if (fragmentManager.onBackPressed()) {
				toolbarResolver.updateIcon();
			} else {
				exit();
			}
		}
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
				fragmentManager.showRootFragment(fragment);
			});
		} else {
			fragmentManager.showRootFragment(fragment);
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
	public void setTargetFragment(int code) {
		fragmentManager.setTargetFragmentCode(code);
	}

	@Override
	public void openActivity(Class<? extends FragmentActivity> aClass) {
		currentActivity.startActivity(new Intent(currentActivity, aClass));
		currentActivity.finish();
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		currentActivity.startActivityForResult(intent, requestCode);
	}

	@Override
	public void startActivityForResultFormFragment(Intent intent, int requestCode) {
		fragmentManager.startActivityForResult(intent, requestCode);
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

	private void exit() {
		if (doubleBackToExitPressedOnce) {
			currentActivity.finish();
		} else {
			uiResolver.showToast(R.string.exit_toast);
			doubleBackToExitPressedOnce = true;
			just(null).delay(1000, TimeUnit.MILLISECONDS)
					.subscribe(o -> doubleBackToExitPressedOnce = false);
		}
	}

	private static class MyFragmentResolverCallback
			implements FragmentResolver.FragmentResolverCallback {
		private final ToolbarResolver toolbarResolver;

		public MyFragmentResolverCallback(ToolbarResolver toolbarResolver) {
			this.toolbarResolver = toolbarResolver;
		}

		@Override
		public void onRootFragment() {
			toolbarResolver.showHomeIcon();
		}

		@Override
		public void onNotRootFragment() {
			toolbarResolver.showBackIcon();
		}
	}

	private class MyToolbarResolverCallback implements ToolbarResolver.ToolbarResolverCallback {
		private final FragmentResolver fragmentManager;
		private final LeftDrawerHelper drawerHelper;
		private final BaseActivityView activityView;
		private final ToolbarResolver toolbarResolver;

		public MyToolbarResolverCallback(FragmentResolver fragmentManager,
										 LeftDrawerHelper drawerHelper,
										 BaseActivityView activityView,
										 ToolbarResolver toolbarResolver) {
			this.fragmentManager = fragmentManager;
			this.drawerHelper = drawerHelper;
			this.activityView = activityView;
			this.toolbarResolver = toolbarResolver;
		}

		@Override
		public void onClickHome() {
			if (fragmentManager.isRootScreen()) {
				drawerHelper.open();
			} else {
				onBackPressed();
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
}
