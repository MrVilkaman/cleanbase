package com.github.mrvilkaman.presentationlayer.resolution.drawer;

import android.support.annotation.IdRes;

import com.github.mrvilkaman.presentationlayer.fragments.core.IBaseScreen;

public interface LeftDrawerHelper {

	@IdRes
	int getDrawerContentFrame();

	boolean isOpen();

	void close();

	void close(LeftDrawerHelperCallback leftDrawerHelperCallback);

	int getDrawerLayout();

	void open();

	boolean hasDrawer();

	IBaseScreen getDrawerFragment();

	interface LeftDrawerHelperCallback {
		void onClose();
	}
}
