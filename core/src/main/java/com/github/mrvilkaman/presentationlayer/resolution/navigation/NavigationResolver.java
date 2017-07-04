package com.github.mrvilkaman.presentationlayer.resolution.navigation;

import android.app.Activity;
import android.content.Intent;

import com.github.mrvilkaman.presentationlayer.fragments.core.IBaseScreen;

public interface NavigationResolver {

	void init();

	void showFragment(IBaseScreen fragment);

	void showRootFragment(IBaseScreen fragment);

	void showFragmentWithoutBackStack(IBaseScreen fragment);

	void setTargetFragment(int cropPhotoRequestCode);

	void onBackPressed();

	void openLinkInBrowser(String link);

	void back();

	void openActivity(Class<? extends Activity> aClass);

	void startActivity(Class<? extends Activity> aClass);

	void startActivityForResultFormFragment(Intent intent, int requestCode);

	void startActivityForResult(Intent intent, int requestCode);

}
