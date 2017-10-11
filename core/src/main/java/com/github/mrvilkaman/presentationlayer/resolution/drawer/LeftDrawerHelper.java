package com.github.mrvilkaman.presentationlayer.resolution.drawer;

import android.support.annotation.IdRes;

public interface LeftDrawerHelper {

	@IdRes
	int getDrawerContentFrame();

	boolean isOpen();

	void close();

	void close(LeftDrawerHelperCallback leftDrawerHelperCallback);

	int getDrawerLayout();

	void open();

	boolean hasDrawer();

	void setNeedDrawerSlide(boolean needDrawerSlide);

	interface LeftDrawerHelperCallback {
		void onClose();
	}
}
