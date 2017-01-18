package com.github.mrvilkaman.presentationlayer.fragments.simplelist;


import com.github.mrvilkaman.presentationlayer.fragments.core.BaseDiffCallback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Override
	protected Set<String> getChangePayload(SimpleModel newItem, SimpleModel oldItem) {
		Set<String> set = new HashSet<>(2);
		put(set, newItem.getNumber(), oldItem.getNumber(), "number");
		put(set, newItem.getImage(), oldItem.getImage(), "image");
		put(set, newItem.getValue(), oldItem.getValue(), "value");

		if (set.isEmpty()) {
			return super.getChangePayload(newItem, oldItem);
		} else {
			return set;
		}
	}

	private void put(Set<String> set, Object number, Object numberOld, String key) {
		if (number != numberOld) {
			set.add(key);
		}
	}
}
