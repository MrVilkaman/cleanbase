package ru.fixapp.fooproject.presentationlayer.activities;


import android.support.annotation.DrawableRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;
import ru.fixapp.fooproject.presentationlayer.utils.IToolbar;

public abstract class ToolbarActivity extends BaseActivity {

	private Toolbar toolbar;
	private ToolbarMenuHelper toolbarMenuHelper = new ToolbarMenuHelper(this::invalidateOptionsMenu);

	@Override
	protected BaseFragment createStartFragment() {
		return null;
	}

	@Override
	public IToolbar getToolbar() {
		return new MyIToolbar();
	}

	@Override
	protected void updateIcon() {
		IToolbar toolbar = getToolbar();
		if (toolbar != null) {
			if (hasChild()) {
				showBackIcon();
			} else {
				showHomeIcon();
			}
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		toolbarMenuHelper.onPrepareOptionsMenu(menu);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		toolbarMenuHelper.onOptionsItemSelected(item);

		return super.onOptionsItemSelected(item);
	}

	private void showHomeIcon() {
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
	}

	private void showBackIcon() {
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
	}

	@Override
	protected void configureToolBar() {
		toolbar = ButterKnife.findById(this, R.id.toolbar_actionbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setDisplayShowHomeEnabled(true);
		toolbar.setNavigationOnClickListener(v -> {
			if (hasChild()) {
				onBackPressed();
			} else {
				hideKeyboard();
				if (drawerLayout != null)
					drawerLayout.openDrawer(Gravity.LEFT);
			}
		});

		final View view = findViewById(R.id.all_content);
		if (drawerLayout != null)

			drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					if (drawerView.getId() == getDrawerContentFrame()) {
						float moveFactor = drawerView.getWidth() * slideOffset;
						view.setTranslationX(moveFactor);
					}
				}

				@Override
				public void onDrawerClosed(View drawerView) {
					nextFragment();
				}

				@Override
				public void onDrawerOpened(View drawerView) {

				}

				@Override
				public void onDrawerStateChanged(int newState) {

				}
			});

	}

	@Override
	void nextFragment() {
		if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.LEFT)) {
			drawerLayout.closeDrawers();
		} else {
			toolbarMenuHelper.clear();
			super.nextFragment();
		}
	}

	private class MyIToolbar implements IToolbar {

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
			getSupportActionBar().setTitle(text);
		}

		@Override
		public void setText(String text) {
			getSupportActionBar().setTitle(text);
		}

		@Override
		public void hideHomeButton() {
			ActionBar supportActionBar = getSupportActionBar();
			supportActionBar.setHomeButtonEnabled(false);
			supportActionBar.setDisplayHomeAsUpEnabled(false);
		}

		@Override
		public void showHomeButton() {
			ActionBar supportActionBar = getSupportActionBar();
			supportActionBar.setHomeButtonEnabled(true);
			supportActionBar.setDisplayShowHomeEnabled(true);
//			supportActionBar.setDisplayHomeAsUpEnabled(false);
			updateIcon();
		}

		@Override
		public void showIcon(@DrawableRes int res,Runnable callback) {
			toolbarMenuHelper.showIcon(res,callback);
		}

		@Override
		public void remove(@DrawableRes int resId) {
			toolbarMenuHelper.remove(resId);
		}
	}
}
