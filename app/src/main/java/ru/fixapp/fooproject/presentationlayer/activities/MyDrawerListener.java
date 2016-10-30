package ru.fixapp.fooproject.presentationlayer.activities;


import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class MyDrawerListener implements DrawerLayout.DrawerListener {

	private final View view;
	private int drawerContentFrame;


	public MyDrawerListener(View view, int drawerContentFrame) {
		this.view = view;
		this.drawerContentFrame = drawerContentFrame;
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		if (drawerView.getId() == drawerContentFrame) {
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
