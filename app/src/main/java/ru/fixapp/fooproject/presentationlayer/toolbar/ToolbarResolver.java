package ru.fixapp.fooproject.presentationlayer.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public interface ToolbarResolver {

	void setCallback(ToolbarResolverCallback callback);

	void setTitle(int text);

	void setTitle(String text);

	void hideHomeButton();

	void showHomeButton();

	interface ToolbarResolverCallback{
		void onClickHome();
		void updateIcon();
	}

	void init(View view, AppCompatActivity activity);

	void onPrepareOptionsMenu(Menu menu);

	void onOptionsItemSelected(MenuItem item);

	void updateIcon();

	void clear();

	void showHomeIcon();

	void showBackIcon();
}
