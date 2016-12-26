package com.github.mrvilkaman.presentationlayer.fragments.photomaker;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.activities.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.photocrop.CropImageFragment;
import com.github.mrvilkaman.presentationlayer.photoulits.PhotoHelper;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;

public class PhotoMakerScreenFragment extends BaseFragment<PhotoMakerPresenter>
		implements PhotoMakerView {

	@Inject PhotoHelper photoHelper;
	@Inject ImageLoader imageLoader;

	@BindView(R.id.image_view) ImageView imageView;

	public static PhotoMakerScreenFragment open() {
		return new PhotoMakerScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_photomakerscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		getPresenter().init(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getPresenter().saveInstanceState(outState);
	}

	@OnClick(R.id.photo_take)
	void onClickTake() {
		photoHelper.openCamera(CropImageFragment.MODE.FREE);
	}

	@OnClick(R.id.photo_gallary)
	void onClickGallary() {
		photoHelper.openGallery(CropImageFragment.MODE.FREE);
	}

	@Override
	public void showImage(String lastPath) {
		imageLoader.showFromFile(lastPath,imageView);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		photoHelper.onActivityResult(requestCode, resultCode, data,
				path -> getPresenter().loadFile(path));
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerPhotoMakerScreenComponent.builder().activityCoreComponent(component).build().inject(this);
	}

}