package ru.fixapp.fooproject.presentationlayer.resolution;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;

public class ToolbarResolverImpl implements ToolbarResolver{

	private final Toolbar toolbar;
	private final ActionBar supportActionBar;
	private final ToolbarMenuHelper toolbarMenuHelper;

	private ToolbarResolverCallback callback;

	public ToolbarResolverImpl(View view, AppCompatActivity activity, ToolbarMenuHelper toolbarMenuHelper) {
		toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
		activity.setSupportActionBar(toolbar);
		supportActionBar = activity.getSupportActionBar();
		supportActionBar.setHomeButtonEnabled(true);
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(v -> {
			if (callback != null)
				callback.onClickHome();
		});
		this.toolbarMenuHelper = toolbarMenuHelper;
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
		supportActionBar.setHomeAsUpIndicator(R.drawable.ic_home);
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
	public void setTitle(int text) {
		supportActionBar.setTitle(text);
	}

	@Override
	public void setTitle(String text) {
		supportActionBar.setTitle(text);
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
}
