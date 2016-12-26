package ru.fixapp.fooproject.presentationlayer.resolution.drawer;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.github.mrvilkaman.core.R;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.ProvideFragmentCallback;

public class LeftDrawerHelperImpl implements LeftDrawerHelper, DrawerLayout.DrawerListener {


	private static final int MAIN_GRAVITY = Gravity.LEFT;
	LeftDrawerHelperCallback leftDrawerHelperCallback;
	private DrawerLayout drawerLayout;
	private View contentView;
	private final ProvideFragmentCallback callback;


	public LeftDrawerHelperImpl(ProvideFragmentCallback callback) {
		this.callback = callback;
	}

	@Override
	public void init(@NonNull View rootView) {
		this.drawerLayout = (DrawerLayout) rootView.findViewById(getDrawerLayout());
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
	public BaseFragment getDrawerFragment(){
		return callback.createFragment();
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
