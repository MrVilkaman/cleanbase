package com.github.mrvilkaman.presentationlayer.fragments.simplelist;


import com.github.mrvilkaman.presentationlayer.fragments.core.BaseDiffCallback;

import java.util.List;

public class SimpleDiffUtils extends BaseDiffCallback<SimpleModel> {
	public SimpleDiffUtils(List<SimpleModel> mOldList, List<SimpleModel> mNewList) {
		super(mOldList, mNewList);
	}

	@Override
	protected boolean areItemsTheSame(SimpleModel oldItem, SimpleModel newItem) {
		return oldItem.getNumber() == newItem.getNumber();
	}

	@Override
	protected boolean areContentsTheSame(SimpleModel oldItem, SimpleModel newItem) {
		return super.areContentsTheSame(oldItem, newItem);
	}
}
