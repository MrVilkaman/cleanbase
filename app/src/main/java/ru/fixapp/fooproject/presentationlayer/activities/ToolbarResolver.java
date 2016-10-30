package ru.fixapp.fooproject.presentationlayer.activities;

import android.view.Menu;
import android.view.MenuItem;

public interface ToolbarResolver {

	void setCallback(ToolbarResolverCallback callback);

	interface ToolbarResolverCallback{
		void onClickHome();
		void updateIcon();
	}

	void onPrepareOptionsMenu(Menu menu);

	void onOptionsItemSelected(MenuItem item);

	void updateIcon();

	void clear();

	void showHomeIcon();

	void showBackIcon();
}
