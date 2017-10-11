package com.github.mrvilkaman.presentationlayer.resolution.drawer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.di.INeedActivityViewNotify;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

public class LeftDrawerHelperImpl implements LeftDrawerHelper, DrawerLayout.DrawerListener,INeedActivityViewNotify {


	private static final int MAIN_GRAVITY = Gravity.LEFT;
	private LeftDrawerHelperCallback leftDrawerHelperCallback;
	private DrawerLayout drawerLayout;
	private View contentView;
	private boolean needDrawerSlide = true;

	private AppCompatActivity activity;

	public LeftDrawerHelperImpl(AppCompatActivity activity) {
		this.activity = activity;
	}

	@Override
	public int getInitPriority() {
		return INeedActivityViewNotify.INIT_PRIORITY_FIRST;
	}

	@Override
	public void onInit() {
		View rootView = DevUtils.getRootView(activity);
		this.drawerLayout = rootView
				.findViewById(getDrawerLayout());
		contentView = rootView.findViewById(R.id.all_content);
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
		if (needDrawerSlide) {
			if (drawerView.getId() == getDrawerContentFrame()) {
				float moveFactor = drawerView.getWidth() * slideOffset;
				contentView.setTranslationX(moveFactor);
			}
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

	@Override
	public void setNeedDrawerSlide(boolean needDrawerSlide) {
		this.needDrawerSlide = needDrawerSlide;
	}
}
