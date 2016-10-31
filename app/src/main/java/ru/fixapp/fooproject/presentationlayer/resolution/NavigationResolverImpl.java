package ru.fixapp.fooproject.presentationlayer.resolution;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.TimeUnit;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ToolbarResolver;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.Frag1ScreenFragment;

import static rx.Observable.just;


public class NavigationResolverImpl implements NavigationResolver {

	protected boolean doubleBackToExitPressedOnce;
	private Activity currentActivity;
	private FragmentResolver fragmentManager;
	private LeftDrawerHelper drawerHelper;
	private ToolbarResolver toolbarResolver;
	private UIResolver uiResolver;

	public NavigationResolverImpl(Activity currentActivity, FragmentResolver fragmentManager,
								  LeftDrawerHelper drawerHelper, ToolbarResolver toolbarResolver,
								  UIResolver uiResolver) {
		this.currentActivity = currentActivity;
		this.fragmentManager = fragmentManager;
		this.drawerHelper = drawerHelper;
		this.toolbarResolver = toolbarResolver;
		this.uiResolver = uiResolver;


		if(!fragmentManager.hasFragment()){
			fragmentManager.showRootFragment(createStartFragment());

			if(drawerHelper.hasDrawer()){
				fragmentManager.addDrawer(drawerHelper.getDrawerContentFrame(), drawerHelper.getDrawerFragment());
			}
		}

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
	public BaseFragment createStartFragment() {
		return Frag1ScreenFragment.open();
	}

	@Override
	public boolean onBackPressed() {
		boolean b = fragmentManager.onBackPressed();

		if (!b) {
			exit();
		} else {
			toolbarResolver.updateIcon();
		}

		return b;
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
}
