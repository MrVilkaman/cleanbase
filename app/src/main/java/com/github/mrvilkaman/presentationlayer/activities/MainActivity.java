package com.github.mrvilkaman.presentationlayer.activities;

import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerActivityComponent;
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.fragments.testfrags.DrawerScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.testfrags.Frag1ScreenFragment;


public class MainActivity extends BaseActivity<ActivityComponent> {


	@Override
	protected ActivityComponent createComponent() {
		AppComponent appComponent = getComponent(AppComponent.class);
		View rootView = getRootView();
		CommonActivityModule commonActivityModule =
				new CommonActivityModule(this, this, rootView, getSupportFragmentManager(),
						getContainerID(), Frag1ScreenFragment::open);

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
