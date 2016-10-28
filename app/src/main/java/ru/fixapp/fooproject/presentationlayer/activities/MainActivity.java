package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.IHasComponent;
import ru.fixapp.fooproject.presentationlayer.app.App;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.hello.HelloScreenFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;

/**
 * Created by root on 12.03.16.
 */
public class MainActivity extends BaseActivity implements IHasComponent<ActivityScreenComponent> {

	private ActivityScreenComponent screenComponent;

	@Override
	protected void injectDagger() {
		AppComponent appComponent = App.get(this).getAppComponent();
		screenComponent =
				DaggerActivityScreenComponent.builder().appComponent(appComponent).build();
		screenComponent.inject(this);
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

	@Override
	public ActivityScreenComponent getComponent() {
		return screenComponent;
	}
}
