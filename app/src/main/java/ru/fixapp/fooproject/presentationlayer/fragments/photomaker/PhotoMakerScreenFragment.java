package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;
import ru.fixapp.fooproject.presentationlayer.utils.PhotoUtils;

public class PhotoMakerScreenFragment extends BaseFragment<PhotoMakerPresenter>
		implements PhotoMakerView {

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

	}

	@OnClick(R.id.photo_take)
	void onClickTake() {
		PhotoUtils.openCamera(this, CropImageFragment.MODE.FREE);
	}

	@OnClick(R.id.photo_gallary)
	void onClickGallary() {
		PhotoUtils.openGallery(this, CropImageFragment.MODE.FREE);
	}

	@Override
	public void showImage(String lastPath) {
		Picasso.with(getActivity()).load(new File(lastPath))
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PhotoUtils.CROP_PHOTO_REQUEST_CODE && Activity.RESULT_OK == resultCode) {
			String lastPath =
					PhotoUtils.getPathToTempFiles(getActivity()) + PhotoUtils.getLastFileName();
			getPresenter().loadFile(lastPath);
		}

		PhotoUtils.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void daggerInject(ActivityComponent component) {
		DaggerPhotoMakerScreenComponent.builder().activityComponent(component).build().inject(this);
	}

}