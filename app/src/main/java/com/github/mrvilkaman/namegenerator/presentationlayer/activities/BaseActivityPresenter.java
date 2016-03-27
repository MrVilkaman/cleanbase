package com.github.mrvilkaman.namegenerator.presentationlayer.activities;

import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BaseFragment;

public interface BaseActivityPresenter {

	void loadRootFragment(BaseFragment fragment, boolean addToBackStack, boolean isRoot, boolean forceLoad, boolean openIfcreated);
}
