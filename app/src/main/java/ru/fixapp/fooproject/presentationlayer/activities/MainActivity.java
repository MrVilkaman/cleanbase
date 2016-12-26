package ru.fixapp.fooproject.presentationlayer.activities;

import android.view.View;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.di.ActivityComponent;
import ru.fixapp.fooproject.di.AppComponent;
import ru.fixapp.fooproject.di.DaggerActivityComponent;
import ru.fixapp.fooproject.di.modules.activity.CommonActivityModule;
import ru.fixapp.fooproject.di.modules.activity.DrawerModule;
import ru.fixapp.fooproject.di.modules.activity.ToolbarModule;
import ru.fixapp.fooproject.presentationlayer.fragments.photomaker.PhotoMakerScreenFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.testfrags.DrawerScreenFragment;


public class MainActivity extends BaseActivity<ActivityComponent> {


	@Override
	protected ActivityComponent createComponent() {
		AppComponent appComponent = getComponent(AppComponent.class);
		View rootView = getRootView();
		CommonActivityModule commonActivityModule =
				new CommonActivityModule(this, this, rootView, getSupportFragmentManager(),
						getContainerID(), PhotoMakerScreenFragment::open);

		return DaggerActivityComponent.builder()
				.appComponent(appComponent)
				.commonActivityModule(commonActivityModule)
				.toolbarModule(new ToolbarModule(rootView, this))
				.drawerModule(new DrawerModule(DrawerScreenFragment::open))
				.build();
	}

	@Override
	protected void injectMe(ActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	protected int getActivityLayoutResourceID() {
		return R.layout.activity_main_app;
	}
}
