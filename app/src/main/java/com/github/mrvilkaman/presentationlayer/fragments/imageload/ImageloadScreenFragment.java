package com.github.mrvilkaman.presentationlayer.fragments.imageload;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;

public class ImageloadScreenFragment extends BaseFragment<ImageloadPresenter>
		implements ImageloadView {

	public static final String URL = "http://file.mobilmusic.ru/9f/f4/f3/561289.jpg";
	@BindView(R.id.image_view_1) ImageView view1;
	@BindView(R.id.image_view_2) ImageView view2;
	@BindView(R.id.image_view_3) ImageView view3;
	@BindView(R.id.image_view_4) ImageView view4;
	@BindView(R.id.image_view_5) ImageView view5;

	@Inject ImageLoader imageLoader;

	public static ImageloadScreenFragment open() {
		return new ImageloadScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_imageloadscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

		loadFixWight();
		loadFixHeight();
		loadFixAll();
		loadError();
	}

	private void loadError() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		imageLoader.load("http://file.mobilmusic.ru/g")
				.size(dimension, dimension)
				.holder(R.drawable.ic_picasso_placeholder)
				.error(R.drawable.ic_picasso_error)
				.into(view5);
	}


	private void loadFixWight() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		imageLoader.load(URL)
				.height(dimension)
				.holder(R.drawable.ic_picasso_placeholder)
				.error(R.drawable.ic_picasso_error)
				.into(view1);
	}

	private void loadFixHeight() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		imageLoader.load(URL)
				.width(dimension)
				.holder(R.drawable.ic_picasso_placeholder)
				.error(R.drawable.ic_picasso_error)
				.into(view2);
	}

	private void loadFixAll() {
		int dimension = (int) getResources().getDimension(R.dimen.image_height);
		imageLoader.load(URL)
				.size(dimension,dimension)
				.into(view3);
	}

	@Override
	public void daggerInject() {
		ActivityCoreComponent component = getComponent(ActivityCoreComponent.class);
		DaggerImageloadScreenComponent.builder()
				.activityCoreComponent(component)
				.build()
				.inject(this);
	}

}