package com.github.mrvilkaman.presentationlayer.resolution.toolbar;


import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ToolbarResolverEmpty implements ToolbarResolver {

	@Override
	public void init(View view, AppCompatActivity activity) {

	}

	@Override
	public void setCallback(ToolbarResolverCallback callback) {

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

	}

	@Override
	public void onOptionsItemSelected(MenuItem item) {

	}

	@Override
	public void updateIcon() {

	}

	@Override
	public void clear() {

	}

	@Override
	public void showHomeIcon() {

	}

	@Override
	public void showBackIcon() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	@Override
	public void hideHomeButton() {

	}

	@Override
	public void showHomeButton() {

	}

	@Override
	public void setTitle(@StringRes int text) {

	}

	@Override
	public void setTitle(String text) {

	}

	@Override
	public void showIcon(@DrawableRes int Id, Runnable callback) {

	}

	@Override
	public void remove(@DrawableRes int resId) {

	}
}
