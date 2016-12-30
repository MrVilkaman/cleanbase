package com.github.mrvilkaman.presentationlayer.fragments.imageload;


import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.BindView;

public class ImageloadScreenFragment extends BaseFragment<ImageloadPresenter>
		implements ImageloadView {

	public static final String URL = "http://file.mobilmusic.ru/9f/f4/f3/561289.jpg";
	@BindView(R.id.image_view_1) ImageView view1;
	@BindView(R.id.image_view_2) ImageView view2;
	@BindView(R.id.image_view_3) ImageView view3;
	@BindView(R.id.image_view_4) ImageView view4;
	@BindView(R.id.image_view_5) ImageView view5;
	private Picasso picasso;

	public static ImageloadScreenFragment open() {
		return new ImageloadScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_imageloadscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

		picasso = Picasso.with(getContext());
		picasso.setIndicatorsEnabled(true);
		picasso.setLoggingEnabled(true);
		picasso.invalidate(URL);

		loadFixWight();
		loadFixHeight();
		loadFixAll();
		loadError();
	}

	private void loadError() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		load(Uri.parse("http://file.mobilmusic.ru/g"), dimension, dimension, view5,
				R.drawable.ic_picasso_placeholder, R.drawable.ic_picasso_error);
	}

	private void load(Uri uri, int width, int height, ImageView target, int placeholderResId,
					  int errorResId) {
		RequestCreator load = picasso.load(uri);
		if (0 < width && 0 < height) {
			load = load.centerCrop();
		}
		if (0 != width || 0 != height) {
			load = load.resize(width, height)
					.onlyScaleDown();
		}

		load.placeholder(placeholderResId)
				.error(errorResId)
				.into(target);
	}

	private void loadFixWight() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		load(Uri.parse(URL), 0, dimension, view1, R.drawable.ic_picasso_placeholder,
				R.drawable.ic_picasso_error);
	}

	private void loadFixHeight() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		load(Uri.parse(URL), dimension, 0, view2, R.drawable.ic_picasso_placeholder,
				R.drawable.ic_picasso_error);
	}

	private void loadFixAll() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		load(Uri.parse(URL), dimension, dimension, view3, R.drawable.ic_picasso_placeholder,
				R.drawable.ic_picasso_error);
	}

	@Override
	public void daggerInject() {
		ActivityComponent component = getComponent(ActivityComponent.class);
		DaggerImageloadScreenComponent.builder()
				.activityComponent(component)
				.build()
				.inject(this);
	}

}