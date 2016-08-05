package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.hello.HelloScreenFragment;
import ru.fixapp.fooproject.presentationlayer.utils.IToolbar;

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
