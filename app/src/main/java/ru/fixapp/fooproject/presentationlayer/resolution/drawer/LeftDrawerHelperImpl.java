package ru.fixapp.fooproject.presentationlayer.resolution.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public abstract class LeftDrawerHelperImpl implements LeftDrawerHelper, DrawerLayout.DrawerListener {


	private static final int MAIN_GRAVITY = Gravity.LEFT;
	LeftDrawerHelperCallback leftDrawerHelperCallback;
	private DrawerLayout drawerLayout;
	private View contentView;


	public LeftDrawerHelperImpl(View rootView) {
		this.drawerLayout = (DrawerLayout) rootView.findViewById(getDrawerLayout());
		contentView = rootView.findViewById(R.id.all_content);
	}

	// NOTE: call once before use!
	public void init() {
		this.drawerLayout.addDrawerListener(this);
	}

	@Override
	public void open() {
		drawerLayout.openDrawer(MAIN_GRAVITY);
	}

	@Override
	public int getDrawerContentFrame() {
		return R.id.menu_frame;
	}

	@Override
	public boolean hasDrawer() {
		return true;
	}

	@Override
	public abstract BaseFragment getDrawerFragment();

	@Override
	public boolean isOpen() {
		return drawerLayout.isDrawerOpen(MAIN_GRAVITY);
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
