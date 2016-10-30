package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.annotation.IdRes;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import ru.fixapp.fooproject.R;

public class LeftDrawerHelper {

	private DrawerLayout drawerLayout;

	public void init(View rootView) {
		this.drawerLayout = (DrawerLayout) rootView.findViewById(getDrawerLayout());

		View contentView = rootView.findViewById(R.id.all_content);
		this.drawerLayout.addDrawerListener(new MyDrawerListener(contentView, getDrawerContentFrame()));
	}

	@IdRes
	public int getDrawerContentFrame() {
		return R.id.menu_frame;
	}

	public void close() {
		drawerLayout.openDrawer(Gravity.LEFT);
	}


	public int getDrawerLayout() {
		return 0;
	}
}
