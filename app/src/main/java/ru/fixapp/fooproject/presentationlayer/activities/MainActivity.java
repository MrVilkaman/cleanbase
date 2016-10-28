package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.IHasComponent;
import ru.fixapp.fooproject.presentationlayer.app.App;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.Frag1ScreenFragment;

/**
 * Created by root on 12.03.16.
 */
public class MainActivity extends ToolbarActivity implements IHasComponent<ActivityComponent> {

	private ActivityComponent screenComponent;

	@Override
	protected void injectDagger() {
		Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar_actionbar);

		AppComponent appComponent = App.get(this).getAppComponent();
		screenComponent = DaggerActivityComponent.builder().appComponent(appComponent)
				.activityModule(new ActivityComponent.ActivityModule(getRootView(), this,
						getSupportFragmentManager(), getContainerID(), this::invalidateOptionsMenu,toolbar,this))
				.build();
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
	public ActivityComponent getComponent() {
		return screenComponent;
	}
}
