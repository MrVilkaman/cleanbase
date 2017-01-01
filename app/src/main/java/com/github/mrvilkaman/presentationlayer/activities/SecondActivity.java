package com.github.mrvilkaman.presentationlayer.activities;


import android.view.View;

import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerSecondActivityComponent;
import com.github.mrvilkaman.di.SecondActivityComponent;
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.DrawerModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.fragments.imageload.ImageloadScreenFragment;
import com.github.mrvilkaman.presentationlayer.fragments.testfrags.DrawerScreenFragment;

public class SecondActivity extends BaseActivity<SecondActivityComponent> {

	@Override
	protected void injectMe(SecondActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	@Override
	protected SecondActivityComponent createComponent() {

		AppComponent appComponent = getComponent(AppComponent.class);
		View rootView = getRootView();
		CommonActivityModule commonActivityModule =
				new CommonActivityModule(this, this, rootView, getSupportFragmentManager(),
						getContainerID(), ImageloadScreenFragment::open);

		return DaggerSecondActivityComponent.builder()
				.appComponent(appComponent)
				.commonActivityModule(commonActivityModule)
				.toolbarModule(new ToolbarModule(rootView, this))
				.drawerModule(new DrawerModule(DrawerScreenFragment::open))
				.build();
	}

}

