package com.github.mrvilkaman.presentationlayer.resolution;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.di.INeedActivityViewNotify;
import com.github.mrvilkaman.presentationlayer.fragments.core.OnBackPressedListener;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

public abstract class BaseNavigator extends SupportAppNavigator implements INeedActivityViewNotify {


	private final AppCompatActivity activity;
	private final int containerId;
	@Nullable private final ToolbarResolver toolbarResolver;
	@Nullable private final LeftDrawerHelper leftDrawerHelper;
	private final FragmentManager fragmentManager;

	private boolean doubleBackToExitPressedOnce = false;
	private ToolbarResolver.ToolbarResolverCallback value = new ToolbarResolver.ToolbarResolverCallback() {
		@Override
		public void onClickHome() {
			if (fragmentManager.getBackStackEntryCount() <= 1 && activity.isTaskRoot()) {
				if (leftDrawerHelper != null) {
					leftDrawerHelper.open();
				}
			} else {
				applyCommand(new Back());
			}

		}

		@Override
		public void updateIcon() {
			if (toolbarResolver != null) {

				if (fragmentManager.getBackStackEntryCount() < 1 && activity.isTaskRoot()) {
					toolbarResolver.showHomeIcon();
				} else {
					toolbarResolver.showBackIcon();
				}
			}
		}
	};

	public BaseNavigator(@Nullable AppCompatActivity activity, @Nullable int containerId,
	                     @Nullable ToolbarResolver toolbarResolver,
	                     @Nullable LeftDrawerHelper leftDrawerHelper
	) {
		super(activity, containerId);
		this.activity = activity;
		this.containerId = containerId;
		this.toolbarResolver = toolbarResolver;
		this.leftDrawerHelper = leftDrawerHelper;
		fragmentManager = activity.getSupportFragmentManager();
	}

	@Override
	protected void exit() {
		if (doubleBackToExitPressedOnce || !activity.isTaskRoot()) {
			activity.finish();
		} else {
			Toast.makeText(activity, R.string.toast_exit, Toast.LENGTH_SHORT)
					.show();

			doubleBackToExitPressedOnce = true;
			Completable.complete()
					.delay(1000, TimeUnit.MILLISECONDS)
					.subscribe(new Action() {
						@Override
						public void run() throws Exception {
							doubleBackToExitPressedOnce = false;
						}
					});
		}
	}

	@Override
	public void applyCommand(@Nullable Command command) {

		if (command instanceof Back) {
			OnBackPressedListener listener = (OnBackPressedListener) getCurrentFragment();
			if (listener != null && listener.onBackPressed()) {
				return;

			}

			if (1 < fragmentManager.getBackStackEntryCount()) {
				clearAndClose(command);
				super.applyCommand(command);
				if (toolbarResolver != null) {

					int backStackEntryCount = fragmentManager.getBackStackEntryCount();
					if (1 < backStackEntryCount) {
						toolbarResolver.showBackIcon();
					} else {
						toolbarResolver.showHomeIcon();
					}
				}

			} else {
				exit();
			}
		} else {
			clearAndClose(command);
			super.applyCommand(command);
			value.updateIcon();
		}
	}

	private void clearAndClose(@Nullable Command command) {
		if (!(command instanceof Back)) {
			if (toolbarResolver != null) {
				toolbarResolver.clear();
			}
			if (leftDrawerHelper != null) {
				leftDrawerHelper.close();
			}
		}
	}

	@Nullable
	private Fragment getCurrentFragment() {
		return fragmentManager.findFragmentById(containerId);
	}

	@Override
	public void onInit() {
		if (getCurrentFragment() == null) {
			String mainScreenKey = getMainScreenKey();
			if (mainScreenKey != null)
				applyCommand(new Forward(mainScreenKey, null));
		}
		value.updateIcon();
		if (toolbarResolver != null) {
			toolbarResolver.setCallback(value);
		}

		if (leftDrawerHelper != null) {
			if (fragmentManager.findFragmentById(leftDrawerHelper.getDrawerContentFrame()) == null) {
				Fragment drawer = getDrawer();
				if (drawer != null) {

					fragmentManager.beginTransaction()
							.add(leftDrawerHelper.getDrawerContentFrame(), drawer)
							.commit();
				}
			}
		}

	}

	@Nullable
	protected abstract Fragment getDrawer();

	@Nullable
	protected abstract String getMainScreenKey();

}
