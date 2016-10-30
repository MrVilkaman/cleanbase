package ru.fixapp.fooproject.presentationlayer.activities;

import android.view.Menu;
import android.view.MenuItem;

public interface ToolbarResolver {
	void onPrepareOptionsMenu(Menu menu);

	void onOptionsItemSelected(MenuItem item);

	void updateIcon();
}
