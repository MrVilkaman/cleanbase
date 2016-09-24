package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.v4.app.FragmentActivity;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface BaseActivityPresenter {

	void loadRootFragment(BaseFragment fragment, boolean addToBackStack, boolean isRoot, boolean forceLoad, boolean openIfcreated);

	void openActivity(Class<? extends FragmentActivity> aClass);
}
