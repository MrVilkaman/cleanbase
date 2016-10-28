package ru.fixapp.fooproject.presentationlayer.activities;

import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.IHasComponent;
import ru.fixapp.fooproject.presentationlayer.app.App;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.Frag1ScreenFragment;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;

/**
 * Created by root on 12.03.16.
 */
public class MainActivity extends BaseActivity implements IHasComponent<ActivityComponent> {

	private ActivityComponent screenComponent;

	@Override
	protected void injectDagger() {
		AppComponent appComponent = App.get(this).getAppComponent();
		screenComponent = DaggerActivityComponent.builder().appComponent(appComponent)
				.activityModule(new ActivityComponent.ActivityModule(getRootView(),this)).build();
		screenComponent.inject(this);
	}

	@Override
	protected BaseFragment createDrawer() {
		return null;
	}

	@Override
	protected BaseFragment createStartFragment() {
		return Frag1ScreenFragment.open();
	}

	@Override
	public IToolbar getToolbar() {
		return null;
	}

	@Override
	public ActivityComponent getComponent() {
		return screenComponent;
	}
}
