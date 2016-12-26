package com.github.mrvilkaman.presentationlayer.resolution.drawer;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;

public interface LeftDrawerHelper {


	void init(@NonNull View rootView);

	@IdRes
	int getDrawerContentFrame();

	boolean isOpen();

	void close();

	void close(LeftDrawerHelperCallback leftDrawerHelperCallback);

	int getDrawerLayout();

	void open();

	boolean hasDrawer();

	BaseFragment getDrawerFragment();

	interface LeftDrawerHelperCallback {
		void onClose();
	}
}
