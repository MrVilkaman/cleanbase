package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.presentationlayer.app.App;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.hello.HelloScreenFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;

/**
 * Created by root on 12.03.16.
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void injectDagger() {
		DaggerActivityScreenComponent.builder()
				.appComponent(App.get(this)
						.getAppComponent())
				.build()
				.inject(this);
	}

	@Override
	protected BaseFragment createDrawer() {
		return null;
	}

	@Override
	protected BaseFragment createStartFragment() {
		return HelloScreenFragment.open();
	}

	@Override
	public IToolbar getToolbar() {
		return null;
	}


}
