package ru.fixapp.fooproject.presentationlayer.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.IHasComponent;
import ru.fixapp.fooproject.di.modules.activity.CommonActivityModule;
import ru.fixapp.fooproject.di.modules.activity.DrawerModule;
import ru.fixapp.fooproject.di.modules.activity.ToolbarModule;
import ru.fixapp.fooproject.presentationlayer.app.App;


public class MainActivity extends BaseActivity implements IHasComponent<ActivityComponent> {

	private ActivityComponent screenComponent;

	@Override
	protected void injectDagger() {
		Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar_actionbar);

		AppComponent appComponent = App.get(this).getAppComponent();
		View rootView = getRootView();
		CommonActivityModule commonActivityModule =
				new CommonActivityModule(this, this, rootView,
						getSupportFragmentManager(), getContainerID());

		screenComponent = DaggerActivityComponent.builder().appComponent(appComponent)
				.commonActivityModule(commonActivityModule)
				.toolbarModule(new ToolbarModule(rootView,this,this::invalidateOptionsMenu,toolbar,this))
				.drawerModule(new DrawerModule(rootView))
				.build();
		screenComponent.inject(this);
	}

	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main_app;
	}

	@Override
	public ActivityComponent getComponent() {
		return screenComponent;
	}
}
