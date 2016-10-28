package ru.fixapp.fooproject.presentationlayer.activities;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.MyToolbarImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;

public abstract class ToolbarActivity extends BaseActivity
		implements MyToolbarImpl.ToolbarCallbacks {

	@Inject ToolbarMenuHelper toolbarMenuHelper;
	private Toolbar toolbar;

	@Override
	protected BaseFragment createStartFragment() {
		return null;
	}

	@Override
	public void updateIcon() {
		ActionBar supportActionBar = getSupportActionBar();
		if (toolbar != null && supportActionBar != null) {
			if (hasChild()) {
				supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back);
			} else {
				getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
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

	@Override
	protected void configureToolBar() {
		toolbar = ButterKnife.findById(this, R.id.toolbar_actionbar);
		setSupportActionBar(toolbar);
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar == null)
			return;
		supportActionBar.setHomeButtonEnabled(true);
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(v -> {
			if (hasChild()) {
				onBackPressed();
			} else {
				hideKeyboard();
				if (drawerLayout != null)
					drawerLayout.openDrawer(Gravity.LEFT);
			}
		});

		if (drawerLayout != null)
			drawerLayout.addDrawerListener(new MyDrawerListener(findViewById(R.id.all_content)));
	}

	// // TODO: 29.10.16 !!!
	//	@Override
	//	void nextFragment() {
	//		if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.LEFT)) {
	//			drawerLayout.closeDrawers();
	//		} else {
	//			toolbarMenuHelper.clear();
	//			super.nextFragment();
	//		}
	//	}

	private class MyDrawerListener implements DrawerLayout.DrawerListener {

		private final View view;

		public MyDrawerListener(View view) {
			this.view = view;
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			if (drawerView.getId() == getDrawerContentFrame()) {
				float moveFactor = drawerView.getWidth() * slideOffset;
				view.setTranslationX(moveFactor);
			}
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			// // TODO: 29.10.16 !!!
			//			nextFragment();
		}

		@Override
		public void onDrawerOpened(View drawerView) {
		}

		@Override
		public void onDrawerStateChanged(int newState) {

		}
	}
}
