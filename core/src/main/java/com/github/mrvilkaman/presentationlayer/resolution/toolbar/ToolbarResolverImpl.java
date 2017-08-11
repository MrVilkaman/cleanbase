package com.github.mrvilkaman.presentationlayer.resolution.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;


public class ToolbarResolverImpl implements ToolbarResolver{

	private final LeftDrawerHelper drawerHelper;
	private final ToolbarMenuHelper toolbarMenuHelper;
	private Toolbar toolbar;
	private ActionBar supportActionBar;

	private ToolbarResolverCallback callback;
	private TextView mTitle;
	private boolean useCustomTitle;

	public ToolbarResolverImpl(ToolbarMenuHelper toolbarMenuHelper, @Nullable LeftDrawerHelper drawerHelper) {
		this.toolbarMenuHelper = toolbarMenuHelper;
		this.drawerHelper = drawerHelper;
	}

	@Override
	public void init(View view, AppCompatActivity activity) {
		toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
		if (toolbar == null) {
			throw new NullPointerException("Can`t find Toolbar with id R.id.toolbar_actionbar!\n Use R.layout.cleanbase_activity_content_with_toolbar");
		}

		mTitle = (TextView) view.findViewById(R.id.toolbar_actionbar_center_title);
		mTitle.setVisibility(View.VISIBLE);

		activity.setSupportActionBar(toolbar);
		supportActionBar = activity.getSupportActionBar();
		if (supportActionBar != null) {
			supportActionBar.setHomeButtonEnabled(true);
			supportActionBar.setDisplayHomeAsUpEnabled(true);
		}
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (callback != null)
					callback.onClickHome();
			}
		});
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		toolbarMenuHelper.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onOptionsItemSelected(MenuItem item) {
		toolbarMenuHelper.onOptionsItemSelected(item);
	}

	@Override
	public void clear() {
		toolbarMenuHelper.clear();
	}

	@Override
	public void updateIcon() {
		if (callback != null)
			callback.updateIcon();
	}

	@Override
	public void showHomeIcon() {
		if (drawerHelper != null && drawerHelper.hasDrawer()) {
			supportActionBar.setHomeAsUpIndicator(R.drawable.ic_home);
		}else{
			supportActionBar.setHomeAsUpIndicator(R.drawable.md_transparent);
		}
	}

	@Override
	public void showBackIcon() {
		supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back);
	}

	@Override
	public void setCallback(ToolbarResolverCallback callback) {
		this.callback = callback;
	}

	@Override
	public void setUseCustomTitle() {
		this.useCustomTitle = true;
		mTitle.setVisibility(View.VISIBLE);
		supportActionBar.setTitle("");
		mTitle.setText(supportActionBar.getTitle());
	}

	@Override
	public void setTitle(int text) {
		if(useCustomTitle) {
			mTitle.setText(text);
		}else {
			supportActionBar.setTitle(text);
		}
	}

	@Override
	public void setTitle(String text) {
		if(useCustomTitle) {
			mTitle.setText(text);
		}else {
			supportActionBar.setTitle(text);
		}
	}

	@Override
	public void hideHomeButton() {
		supportActionBar.setHomeButtonEnabled(false);
		supportActionBar.setDisplayShowHomeEnabled(false);
	}

	@Override
	public void showHomeButton() {
		supportActionBar.setHomeButtonEnabled(true);
		supportActionBar.setDisplayShowHomeEnabled(true);
	}

	@Override
	public void showIcon(@DrawableRes int id, Runnable callback) {
		toolbarMenuHelper.showIcon(id,callback);
	}

	@Override
	public void remove(@DrawableRes int resId) {
		toolbarMenuHelper.remove(resId);
	}

	@Override
	public void hide() {
		toolbar.setVisibility(View.GONE);
	}

	@Override
	public void show() {
		toolbar.setVisibility(View.VISIBLE);
	}
}
