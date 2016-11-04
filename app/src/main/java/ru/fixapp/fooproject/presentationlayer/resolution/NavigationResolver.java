package ru.fixapp.fooproject.presentationlayer.resolution;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface NavigationResolver {

	BaseFragment createStartFragment();

	void showFragment(BaseFragment fragment);

	void showRootFragment(BaseFragment fragment);

	void showFragmentWithoutBackStack(BaseFragment fragment);

	void setTargetFragment(int cropPhotoRequestCode);

	void onBackPressed();

	void back();

	void openActivity(Class<? extends FragmentActivity> aClass);

	void startActivityForResultFormFragment(Intent intent, int requestCode);

	void startActivityForResult(Intent intent, int requestCode);

}
