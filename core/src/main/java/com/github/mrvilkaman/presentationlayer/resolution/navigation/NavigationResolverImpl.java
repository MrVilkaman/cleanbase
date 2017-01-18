package com.github.mrvilkaman.presentationlayer.resolution.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.ProvideFragmentCallback;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.MyFragmentResolverCallback;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.MyToolbarResolverCallback;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;

import java.util.concurrent.TimeUnit;

import static rx.Observable.just;


public class NavigationResolverImpl implements NavigationResolver {

	protected boolean doubleBackToExitPressedOnce;
	private Activity currentActivity;
	private FragmentResolver fragmentManager;
	@Nullable private LeftDrawerHelper drawerHelper;
	@Nullable private ToolbarResolver toolbarResolver;
	private UIResolver uiResolver;
	private BaseActivityView activityView;

	private ProvideFragmentCallback callback;

	public NavigationResolverImpl(Activity currentActivity, FragmentResolver fragmentManager,
								  @Nullable LeftDrawerHelper drawerHelper,
								  @Nullable ToolbarResolver toolbarResolver, UIResolver uiResolver,
								  BaseActivityView activityView, ProvideFragmentCallback
										  callback) {
		this.currentActivity = currentActivity;
		this.fragmentManager = fragmentManager;
		this.drawerHelper = drawerHelper;
		this.toolbarResolver = toolbarResolver;
		this.uiResolver = uiResolver;
		this.activityView = activityView;

		this.callback = callback;
	}

	@Override
	public void init() {

		if (toolbarResolver != null) {
			fragmentManager.setCallback(new MyFragmentResolverCallback(toolbarResolver));
		}

		MyToolbarResolverCallback callback =
				new MyToolbarResolverCallback(fragmentManager, drawerHelper, activityView,
						toolbarResolver, this);
		if (toolbarResolver != null) {
			toolbarResolver.setCallback(callback);
		}


		if (!fragmentManager.hasFragment()) {
			fragmentManager.showRootFragment(this.callback.createFragment());

			if (hasDrawer()) {
				//noinspection ConstantConditions
				fragmentManager.addStaticFragment(drawerHelper.getDrawerContentFrame(),
						drawerHelper.getDrawerFragment());
			}
		}
	}

	private boolean hasDrawer() {
		return drawerHelper != null && drawerHelper.hasDrawer();
	}

	@Override
	public void onBackPressed() {
		if (fragmentManager.processBackFragment()) {
			activityView.hideProgress();
			if (fragmentManager.checkBackStack()) {
				if (toolbarResolver != null)
					toolbarResolver.clear();
				fragmentManager.popBackStack();
				if (toolbarResolver != null)
					toolbarResolver.updateIcon();
			} else {
				exit();
			}
		}
	}

	@Override
	public void showFragment(BaseFragment fragment) {
		LeftDrawerHelper.LeftDrawerHelperCallback callback = () -> {
			if (toolbarResolver != null)
				toolbarResolver.clear();
			fragmentManager.showFragment(fragment);
		};
		close(callback);
	}

	@Override
	public void showRootFragment(BaseFragment fragment) {
		LeftDrawerHelper.LeftDrawerHelperCallback callback = () -> {
			if (toolbarResolver != null)
				toolbarResolver.clear();
			fragmentManager.showRootFragment(fragment);
		};
		close(callback);
	}

	@Override
	public void showFragmentWithoutBackStack(BaseFragment fragment) {
		LeftDrawerHelper.LeftDrawerHelperCallback callback = () -> {
			if (toolbarResolver != null)
				toolbarResolver.clear();
			fragmentManager.showFragmentWithoutBackStack(fragment);
		};
		close(callback);
	}

	private void close(LeftDrawerHelper.LeftDrawerHelperCallback callback) {
		//noinspection ConstantConditions
		if (hasDrawer() && drawerHelper.isOpen()) {
			drawerHelper.close(callback);
		} else {
			callback.onClose();
		}
	}

	@Override
	public void setTargetFragment(int code) {
		fragmentManager.setTargetFragmentCode(code);
	}

	@Override
	public void openActivity(Class<? extends Activity> aClass) {
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
	public void openLinkInBrowser(String link) {
		final String url;
		if (!link.startsWith("http://") && !link.startsWith("https://"))
			url = "http://" + link;
		else
			url = link;

		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		currentActivity.startActivity(browserIntent);
	}


	@Override
	public void back() {
		if (hasDrawer() && drawerHelper.isOpen()) {
			drawerHelper.close();
		} else {
			onBackPressed();
			if (toolbarResolver != null) {
				toolbarResolver.updateIcon();
				toolbarResolver.clear();
			}
		}
	}

	void exit() {

		if (doubleBackToExitPressedOnce || !currentActivity.isTaskRoot()) {
			currentActivity.finish();
		} else {
			uiResolver.showToast(R.string.toast_exit);
			doubleBackToExitPressedOnce = true;
			just(null).delay(1000, TimeUnit.MILLISECONDS)
					.subscribe(o -> doubleBackToExitPressedOnce = false);
		}
	}

}
