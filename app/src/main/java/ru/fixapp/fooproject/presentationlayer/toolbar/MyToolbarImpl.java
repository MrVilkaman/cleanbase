package ru.fixapp.fooproject.presentationlayer.toolbar;


import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.fixapp.fooproject.R;

public class MyToolbarImpl implements IToolbar {

	private  Toolbar toolbar;
	private final ToolbarResolver resolver;
	private final ToolbarMenuHelper toolbarMenuHelper;

	public MyToolbarImpl(View view, ToolbarMenuHelper toolbarMenuHelper,ToolbarResolver resolver) {

		this.resolver = resolver;
		this.toolbarMenuHelper = toolbarMenuHelper;
	}

	@Override
	public void init(View view) {
		this.toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
		if (toolbar == null) {
			throw new NullPointerException("toolbar == null! Cant fing R.id.toolbar_actionbar");
		}
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
		resolver.setTitle(text);
	}


	@Override
	public void setText(String text) {
		resolver.setTitle(text);
	}

	@Override
	public void hideHomeButton() {
		resolver.hideHomeButton();
	}

	@Override
	public void showHomeButton() {
		resolver.showHomeButton();
	}

	@Override
	public void showIcon(@DrawableRes int res, Runnable callback) {
		toolbarMenuHelper.showIcon(res, callback);
	}

	@Override
	public void remove(@DrawableRes int resId) {
		toolbarMenuHelper.remove(resId);
	}
}