package ru.fixapp.fooproject.presentationlayer.resolution;

import android.support.annotation.IdRes;

public interface LeftDrawerHelper {

	@IdRes
	int getDrawerContentFrame();

	boolean isOpen();

	void close();

	void close(LeftDrawerHelperCallback leftDrawerHelperCallback);

	int getDrawerLayout();

	void open();

	interface LeftDrawerHelperCallback {
		void onClose();
	}
}
