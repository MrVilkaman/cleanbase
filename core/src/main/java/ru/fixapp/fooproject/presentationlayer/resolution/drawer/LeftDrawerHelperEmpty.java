package ru.fixapp.fooproject.presentationlayer.resolution.drawer;


import android.support.annotation.NonNull;
import android.view.View;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public class LeftDrawerHelperEmpty implements LeftDrawerHelper {

	@Override
	public void init(@NonNull View rootView) {

	}

	@Override
	public int getDrawerContentFrame() {
		return 0;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public void close() {

	}

	@Override
	public void close(LeftDrawerHelperCallback leftDrawerHelperCallback) {

	}

	@Override
	public int getDrawerLayout() {
		return 0;
	}

	@Override
	public void open() {

	}

	@Override
	public boolean hasDrawer() {
		return false;
	}

	@Override
	public BaseFragment getDrawerFragment() {
		return null;
	}
}
