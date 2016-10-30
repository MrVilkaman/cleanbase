package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.IHasComponent;
import ru.fixapp.fooproject.presentationlayer.app.App;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.DrawerScreenFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.Frag1ScreenFragment;


public class MainActivity extends BaseActivity implements IHasComponent<ActivityComponent> {

	private ActivityComponent screenComponent;

	@Override
	protected void injectDagger() {
		Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar_actionbar);

		AppComponent appComponent = App.get(this).getAppComponent();
		screenComponent = DaggerActivityComponent.builder().appComponent(appComponent)
				.activityModule(new ActivityComponent.ActivityModule(getRootView(), this,
						getSupportFragmentManager(), getContainerID(), this::invalidateOptionsMenu,
						toolbar, this)).build();
		screenComponent.inject(this);
	}

	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main_app;
	}

	@IdRes
	protected int getDrawerContentFrame() {
		return R.id.menu_frame;
	}

	@Override
	protected BaseFragment createDrawer() {
		return DrawerScreenFragment.open();
	}

	@Override
	protected BaseFragment createStartFragment() {
		return Frag1ScreenFragment.open();
	}

	@Override
	public ActivityComponent getComponent() {
		return screenComponent;
	}
}
