package com.github.mrvilkaman.presentationlayer.activities.single;


import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.AppComponent;
import com.github.mrvilkaman.di.DaggerSecondActivityComponent;
import com.github.mrvilkaman.di.SecondActivityComponent;
import com.github.mrvilkaman.di.modules.activity.CommonActivityModule;
import com.github.mrvilkaman.di.modules.activity.FragmentModule;
import com.github.mrvilkaman.di.modules.activity.ToolbarModule;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivity;
import com.github.mrvilkaman.presentationlayer.app.App;
import com.github.mrvilkaman.presentationlayer.fragments.testfrags.DrawerScreenFragment;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleActivity extends BaseActivity<SecondActivityComponent, SinglePresenter>
		implements SingleView {

	@Inject IToolbar toolbar;

	@BindView(R.id.single_some_text) TextView textView;

	@Override
	protected void injectMe(SecondActivityComponent activityComponent) {
		activityComponent.inject(this);
	}

	@Override
	protected SecondActivityComponent createComponent() {

		AppComponent appComponent = DevUtils.getComponent(App.get(this),AppComponent.class);
		View rootView = getRootView();
		CommonActivityModule commonActivityModule =
				new CommonActivityModule(this, this, rootView, () -> null);

		return DaggerSecondActivityComponent.builder()
				.appComponent(appComponent)
				.commonActivityModule(commonActivityModule)
				.fragmentModule(new FragmentModule(getSupportFragmentManager(), getContainerID(),
						resolver -> resolver.addStaticFragment(R.id.single_botton_content,
								DrawerScreenFragment.open())))
				.toolbarModule(new ToolbarModule(rootView, this))
				.build();
	}

	@Override
	@Inject
	public void setPresenter(SinglePresenter presenter) {
		super.setPresenter(presenter);
	}

	@Override
	protected void afterOnCreate() {
		toolbar.setTitle("Activity без фрагмента!");
		ButterKnife.bind(this);
		//noinspection ConstantConditions
		toolbarResolver.setUseCustomTitle();
	}

	@Override
	protected int getActivityLayoutResourceID() {
		return R.layout.activity_single;
	}


	@OnClick(R.id.single_some_btn)
	void onClickSomeBtn() {
		getPresenter().processNextText();
	}

	@Override
	public void bind(String text) {
		textView.setText(text);
	}

}