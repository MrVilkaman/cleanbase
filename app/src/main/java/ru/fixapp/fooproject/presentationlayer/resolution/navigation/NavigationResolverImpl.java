package ru.fixapp.fooproject.presentationlayer.resolution.navigation;

import android.app.Activity;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.photomaker.PhotoMakerScreenFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.FragmentResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.MyFragmentResolverCallback;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.MyToolbarResolverCallback;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.ToolbarResolver;

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

	}

	@Override
	public void init() {

		fragmentManager.setCallback(new MyFragmentResolverCallback(toolbarResolver));

		MyToolbarResolverCallback callback =
				new MyToolbarResolverCallback(fragmentManager, drawerHelper, activityView,
						toolbarResolver, this);
		toolbarResolver.setCallback(callback);


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
		LeftDrawerHelper.LeftDrawerHelperCallback callback = () -> {
			toolbarResolver.clear();
			fragmentManager.showFragment(fragment);
		};
		if (drawerHelper.isOpen()) {
			drawerHelper.close(callback);
		} else {
			callback.onClose();
		}
	}

	@Override
	public void showRootFragment(BaseFragment fragment) {
		LeftDrawerHelper.LeftDrawerHelperCallback callback = () -> {
			toolbarResolver.clear();
			fragmentManager.showRootFragment(fragment);
		};
		if (drawerHelper.isOpen()) {
			drawerHelper.close(callback);
		} else {
			callback.onClose();
		}
	}

	@Override
	public void showFragmentWithoutBackStack(BaseFragment fragment) {
		LeftDrawerHelper.LeftDrawerHelperCallback callback = () -> {
			toolbarResolver.clear();
			fragmentManager.showFragmentWithoutBackStack(fragment);
		};
		if (drawerHelper.isOpen()) {
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
	public void back() {
		if (drawerHelper.isOpen()) {
			drawerHelper.close();
		} else {
			onBackPressed();
			toolbarResolver.updateIcon();
		}
	}

	void exit() {
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
