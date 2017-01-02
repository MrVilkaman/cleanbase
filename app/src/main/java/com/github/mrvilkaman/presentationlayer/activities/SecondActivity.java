package com.github.mrvilkaman.presentationlayer.activities;


import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerSecondActivityComponent;
import com.github.mrvilkaman.di.SecondActivityComponent;
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.presentationlayer.fragments.imageload.ImageloadScreenFragment;

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
				.build();
	}

	@Override
	protected int getActivityLayoutResourceID() {
		return R.layout.cleanbase_activity_content_only;
	}
}

