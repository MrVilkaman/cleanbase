package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public interface BaseActivityPresenter {

	void loadRootFragment(BaseFragment fragment, boolean addToBackStack, boolean isRoot, boolean forceLoad, boolean openIfcreated);
}
