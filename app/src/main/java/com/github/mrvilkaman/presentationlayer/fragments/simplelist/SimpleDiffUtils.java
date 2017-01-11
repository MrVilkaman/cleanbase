package com.github.mrvilkaman.presentationlayer.fragments.simplelist;


import com.github.mrvilkaman.presentationlayer.fragments.core.BaseDiffCallback;

import java.util.List;

public class SimpleDiffUtils extends BaseDiffCallback<String> {
	public SimpleDiffUtils(List<String> mOldList, List<String> mNewList) {
		super(mOldList, mNewList);
	}

	@Override
	protected boolean areItemsTheSame(String oldItem, String newItem) {
		return oldItem == newItem;
	}

	@Override
	protected boolean areContentsTheSame(String oldItem, String newItem) {
		return super.areContentsTheSame(oldItem, newItem);
	}
}
