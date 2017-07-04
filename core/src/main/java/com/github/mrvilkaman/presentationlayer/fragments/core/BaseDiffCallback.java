package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.annotation.Nullable;

import java.util.List;
import java.util.Set;

public abstract class BaseDiffCallback<Type> extends IDiffCallback<Type> {

	private List<Type> mOldList;
	private List<Type> mNewList;

	public BaseDiffCallback() {
	}

	@Override
	public void update(List<Type> oldItems, List<Type> newItems) {
		this.mOldList = oldItems;
		this.mNewList = newItems;
	}

	@Override
	public final int getOldListSize() {
		return mOldList != null ? mOldList.size() : 0;
	}

	@Override
	public final int getNewListSize() {
		return mNewList != null ? mNewList.size() : 0;
	}

	@Override
	public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		Type oldItem = getOldItem(oldItemPosition);
		Type newItem = getNewItem(newItemPosition);
		return areItemsTheSame(oldItem, newItem);
	}

	protected final Type getNewItem(int newItemPosition) {
		return mNewList.get(newItemPosition);
	}

	protected final Type getOldItem(int oldItemPosition) {
		return mOldList.get(oldItemPosition);
	}

	protected abstract boolean areItemsTheSame(Type oldItem, Type newItem);

	protected boolean areContentsTheSame(Type oldItem, Type newItem) {
		return oldItem.equals(newItem);
	}

	@Override
	public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		Type newItem = getNewItem(newItemPosition);
		Type oldItem = getOldItem(oldItemPosition);
		return areContentsTheSame(newItem, oldItem);
	}

	@Nullable
	@Override
	public final Object getChangePayload(int oldItemPosition, int newItemPosition) {
		Type newItem = getNewItem(newItemPosition);
		Type oldItem = getOldItem(oldItemPosition);
		return getChangePayload(newItem, oldItem);
	}

	protected Set<String> getChangePayload(Type newItem, Type oldItem) {
		return null;
	}
}
