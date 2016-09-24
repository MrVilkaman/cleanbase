package ru.fixapp.fooproject.presentationlayer.toolbar;


import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyToolbarImpl implements IToolbar {

	private final ToolbarMenuHelper toolbarMenuHelper;
	private final Toolbar toolbar;
	private final ToolbarCallbacks callbacks;

	public MyToolbarImpl(ToolbarMenuHelper toolbarMenuHelper, Toolbar toolbar, ToolbarCallbacks callbacks) {
		this.toolbarMenuHelper = toolbarMenuHelper;
		this.toolbar = toolbar;
		this.callbacks = callbacks;
	}

	@Override
	public void hide() {
		toolbar.setVisibility(View.GONE);
	}

	@Override
	public void show() {
		toolbar.setVisibility(View.VISIBLE);
	}

	@Override
	public void setText(int text) {
		callbacks.getSupportActionBar().setTitle(text);
	}


	@Override
	public void setText(String text) {
		callbacks.getSupportActionBar().setTitle(text);
	}

	@Override
	public void hideHomeButton() {
		ActionBar supportActionBar = callbacks.getSupportActionBar();
		supportActionBar.setHomeButtonEnabled(false);
		supportActionBar.setDisplayHomeAsUpEnabled(false);
	}

	@Override
	public void showHomeButton() {
		ActionBar supportActionBar = callbacks.getSupportActionBar();
		supportActionBar.setHomeButtonEnabled(true);
		supportActionBar.setDisplayShowHomeEnabled(true);
		callbacks.updateIcon();
	}

	@Override
	public void showIcon(@DrawableRes int res, Runnable callback) {
		toolbarMenuHelper.showIcon(res, callback);
	}

	@Override
	public void remove(@DrawableRes int resId) {
		toolbarMenuHelper.remove(resId);
	}

	public interface ToolbarCallbacks {
		ActionBar getSupportActionBar();
		void updateIcon();
	}

}