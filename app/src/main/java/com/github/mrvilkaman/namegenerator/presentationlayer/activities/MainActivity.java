package com.github.mrvilkaman.namegenerator.presentationlayer.activities;

import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.namegenerator.presentationlayer.fragments.hello.HelloScreenFragment;
import com.github.mrvilkaman.namegenerator.presentationlayer.utils.IToolbar;

/**
 * Created by root on 12.03.16.
 */
public class MainActivity extends BaseActivity {

	@Override
	protected BaseFragment createStartFragment() {
		return HelloScreenFragment.open();
	}

	@Override
	public IToolbar getToolbar() {
		return null;
	}
}
