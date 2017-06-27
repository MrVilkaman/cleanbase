package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import android.support.annotation.Nullable;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.IDiffCallback;
import com.github.mrvilkaman.presentationlayer.fragments.core.MySimpleBaseAdapter;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;

import javax.inject.Inject;

public class SimpleListAdapter extends MySimpleBaseAdapter<SimpleModel, SimpleModelVH> {

	private ImageLoader loader;

	@Inject
	public SimpleListAdapter(ImageLoader loader) {
		this.loader = loader;
	}

	@Override
	protected SimpleModelVH getHolder(View view) {
		return new SimpleModelVH(view, loader);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.simple_list_item;
	}

	@Nullable
	@Override
	protected IDiffCallback<SimpleModel> createDiffCallback() {
		return new SimpleDiffUtils();
	}

	@Override
	public boolean onFailedToRecycleView(SimpleModelVH holder) {
		return true;
	}
}
