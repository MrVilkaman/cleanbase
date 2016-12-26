package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.v7.util.DiffUtil;

import java.util.List;

public abstract class BaseDiffCallback<Type> extends DiffUtil.Callback {

	private List<Type> mOldList;
	private List<Type> mNewList;

	public BaseDiffCallback(List<Type> mOldList, List<Type> mNewList) {
		this.mOldList = mOldList;
		this.mNewList = mNewList;
	}

	@Override
	public int getOldListSize() {
		return mOldList != null ? mOldList.size() : 0;
	}

	@Override
	public int getNewListSize() {
		return mNewList != null ? mNewList.size() : 0;
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		Type oldItem = mOldList.get(oldItemPosition);
		Type newItem = mNewList.get(newItemPosition);
		return areItemsTheSame(oldItem, newItem);
	}

	protected abstract boolean areItemsTheSame(Type oldItem, Type newItem);

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		return mNewList.get(newItemPosition).equals(mOldList.get(oldItemPosition));
	}

}
