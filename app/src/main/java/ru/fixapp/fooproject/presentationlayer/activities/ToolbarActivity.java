package ru.fixapp.fooproject.presentationlayer.activities;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;
import ru.fixapp.fooproject.presentationlayer.toolbar.MyToolbarImpl;
import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarMenuHelper;

public abstract class ToolbarActivity extends BaseActivity implements MyToolbarImpl.ToolbarCallbacks {

	private Toolbar toolbar;
	private ToolbarMenuHelper toolbarMenuHelper = new ToolbarMenuHelper(this::invalidateOptionsMenu);

	@Override
	protected BaseFragment createStartFragment() {
		return null;
	}

	@Override
	public IToolbar getToolbar() {
		return new MyToolbarImpl(toolbarMenuHelper,toolbar,this);
	}

	@Override
	public void updateIcon() {
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

		if (drawerLayout != null)
			drawerLayout.addDrawerListener(new MyDrawerListener(findViewById(R.id.all_content)));
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
			nextFragment();
		}

		@Override
		public void onDrawerOpened(View drawerView) {
		}

		@Override
		public void onDrawerStateChanged(int newState) {

		}
	}
}
