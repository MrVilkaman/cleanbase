package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelper;

public class PhotoMakerScreenFragment extends BaseFragment<PhotoMakerPresenter>
		implements PhotoMakerView {

	@Inject PhotoHelper photoHelper;

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
		photoHelper.openCamera(CropImageFragment.MODE.FREE);
	}

	@OnClick(R.id.photo_gallary)
	void onClickGallary() {
		photoHelper.openGallery(CropImageFragment.MODE.FREE);
	}

	@Override
	public void showImage(String lastPath) {
		Picasso.with(getActivity()).load(new File(lastPath))
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		photoHelper.onActivityResult(requestCode, resultCode, data,
				path -> getPresenter().loadFile(path));
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void daggerInject(ActivityComponent component) {
		DaggerPhotoMakerScreenComponent.builder().activityComponent(component).build().inject(this);
	}

}