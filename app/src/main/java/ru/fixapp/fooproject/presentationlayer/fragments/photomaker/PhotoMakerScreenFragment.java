package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;


import android.os.Bundle;
import android.view.View;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public class PhotoMakerScreenFragment extends BaseFragment<PhotoMakerPresenter>
		implements PhotoMakerView {


	public static PhotoMakerScreenFragment open() {
		return new PhotoMakerScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_photomakerscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

	}

	@Override
	public void daggerInject(ActivityComponent component) {
		DaggerPhotoMakerScreenComponent.builder().activityComponent(component).build().inject(this);
	}

}