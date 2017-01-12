package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import android.support.v7.util.DiffUtil;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.MySimpleBaseAdapter;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;

import java.util.List;

import javax.inject.Inject;

public class SimpleListAdapter extends MySimpleBaseAdapter<SimpleModel, SimpleModelVH> {

	private ImageLoader loader;

	@Inject
	public SimpleListAdapter(ImageLoader loader) {
		this.loader = loader;
//		setHasStableIds(true);
	}

	@Override
	protected SimpleModelVH getHolder(View view) {
		return new SimpleModelVH(view, loader);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.simple_list_item;
	}

	@Override
	protected DiffUtil.Callback getDiffCallback(List<SimpleModel> oldItems,
												List<SimpleModel> newItems) {
		return new SimpleDiffUtils(oldItems, newItems);
	}

	@Override
	public boolean onFailedToRecycleView(SimpleModelVH holder) {
		return true;
//		return super.onFailedToRecycleView(holder);
	}
}
