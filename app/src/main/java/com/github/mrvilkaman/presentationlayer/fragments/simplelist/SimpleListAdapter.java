package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import android.support.v7.util.DiffUtil;

import com.github.mrvilkaman.presentationlayer.fragments.core.MySimpleAdapter;

import java.util.List;

public class SimpleListAdapter extends MySimpleAdapter<String> {

	@Override
	protected DiffUtil.Callback getDiffCallback(List<String> oldItems, List<String> newItems) {
		return new SimpleDiffUtils(oldItems,newItems);
	}
}
