package com.github.mrvilkaman.presentationlayer.resolution.toolbar;

import android.view.Menu;
import android.view.MenuItem;

public interface ToolbarResolver extends IToolbar {

	void setUseCustomTitle();

	interface ToolbarResolverCallback{
		void onClickHome();
		void updateIcon();
	}

	void setCallback(ToolbarResolverCallback callback);

	void onPrepareOptionsMenu(Menu menu);

	void onOptionsItemSelected(MenuItem item);

	void updateIcon();

	void clear();

	void showHomeIcon();

	void showBackIcon();
}
