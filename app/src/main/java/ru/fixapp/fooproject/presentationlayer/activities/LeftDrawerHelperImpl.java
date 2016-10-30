package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.resolution.LeftDrawerHelper;

public class LeftDrawerHelperImpl implements LeftDrawerHelper, DrawerLayout.DrawerListener {


	private DrawerLayout drawerLayout;
	private View contentView;
	private LeftDrawerHelperCallback leftDrawerHelperCallback;


	public LeftDrawerHelperImpl(View rootView) {
		this.drawerLayout = (DrawerLayout) rootView.findViewById(getDrawerLayout());

		contentView = rootView.findViewById(R.id.all_content);
		this.drawerLayout.addDrawerListener(this);
	}

	@Override
	public void open() {
		drawerLayout.openDrawer(Gravity.LEFT);
	}

	@Override
	public int getDrawerContentFrame() {
		return R.id.menu_frame;
	}

	@Override
	public boolean isOpen() {
		return drawerLayout.isDrawerOpen(Gravity.LEFT);
	}

	@Override
	public void close() {
		drawerLayout.closeDrawers();
	}

	@Override
	public void close(LeftDrawerHelperCallback leftDrawerHelperCallback) {
		drawerLayout.closeDrawers();
		this.leftDrawerHelperCallback = leftDrawerHelperCallback;
	}

	@Override
	public int getDrawerLayout() {
		return R.id.drawer_layout;
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		if (drawerView.getId() == getDrawerContentFrame()) {
			float moveFactor = drawerView.getWidth() * slideOffset;
			contentView.setTranslationX(moveFactor);
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {

	}

	@Override
	public void onDrawerClosed(View drawerView) {
		if (leftDrawerHelperCallback != null) {
			leftDrawerHelperCallback.onClose();
			leftDrawerHelperCallback = null;
		}
	}

	@Override
	public void onDrawerStateChanged(int newState) {

	}
}
