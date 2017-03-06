package com.github.mrvilkaman.presentationlayer.activities;


import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerSecondActivityComponent;
import com.github.mrvilkaman.di.SecondActivityComponent;
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.FragmentModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.app.App;
import com.github.mrvilkaman.presentationlayer.fragments.imageload.ImageloadScreenFragment;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

import javax.inject.Inject;

public class SecondActivity extends BaseActivity<SecondActivityComponent, SecondActivityPresenter> {

	@Override
	public void injectMe(SecondActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	@Override
	protected SecondActivityComponent createComponent() {

		AppComponent appComponent = DevUtils.getComponent(App.get(this),AppComponent.class);
		View rootView = getRootView();
		CommonActivityModule commonActivityModule =
				new CommonActivityModule(this, this, rootView, ImageloadScreenFragment::open);

		return DaggerSecondActivityComponent.builder()
				.appComponent(appComponent)
				.commonActivityModule(commonActivityModule)
				.fragmentModule(new FragmentModule(getSupportFragmentManager(),getContainerID()))
				.toolbarModule(new ToolbarModule(rootView, this))
				.build();
	}

	@Override
	@Inject
	public void setPresenter(SecondActivityPresenter presenter) {
		super.setPresenter(presenter);
	}

	@Override
	protected void afterOnCreate() {
		//noinspection ConstantConditions
		toolbarResolver.setUseCustomTitle();
	}

	@Override
	protected int getActivityLayoutResourceID() {
		return R.layout.cleanbase_activity_content_with_toolbar;
	}
}

