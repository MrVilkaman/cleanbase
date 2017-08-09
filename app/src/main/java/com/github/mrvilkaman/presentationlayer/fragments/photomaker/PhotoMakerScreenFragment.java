package com.github.mrvilkaman.presentationlayer.fragments.photomaker;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.photocrop.CropImageFragment;
import com.github.mrvilkaman.presentationlayer.photoulits.PhotoHelper;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;
import com.github.mrvilkaman.presentationlayer.utils.ui.UIUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PhotoMakerScreenFragment extends BaseFragment<PhotoMakerPresenter>
		implements PhotoMakerView {

	@Inject PhotoHelper photoHelper;
	@Inject ImageLoader imageLoader;
	@Inject PermissionManager permissionManager;

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

		permissionManager.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(state -> {
			switch (state) {
				case ALLOWED:
					photoHelper.openGallery(CropImageFragment.MODE.NO);
					break;
				case DENIED:
				case NOT_ASK:
					getUiResolver().showSnackbar(R.string.photo_gallary_denied,
							R.string.cleanbase_setting_btn,
							() -> UIUtils.openSettings(getNavigation(),
									getContext().getPackageName()));
					break;
			}
		});
	}

	@Override
	public void showImage(String lastPath) {
		imageLoader.loadFile(lastPath).into(imageView);
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
		DaggerPhotoMakerScreenComponent.builder()
				.activityCoreComponent(component)
				.build()
				.inject(this);
	}

}