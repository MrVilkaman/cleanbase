package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Zahar on 08.04.16.
 */
public abstract class BaseVH<Type> extends RecyclerView.ViewHolder {


	@Deprecated
	public BaseVH(View view, MySimpleBaseAdapter.OnClickListener<Type> onClick) {
		this(view);
		ButterKnife.bind(this, view);
		setListeners(view, onClick, null);
	}

	public BaseVH(View view) {
		super(view);
		ButterKnife.bind(this, view);
	}

	@SuppressWarnings("unchecked")
	final void setListeners(View view, MySimpleBaseAdapter.OnClickListener<Type> onClick,
					  MySimpleBaseAdapter.OnClickListener<Type> onLongClick) {
		view.setOnClickListener(view1 -> {
			if (onClick != null) {
				onClick.click((Type) view.getTag());
			}
		});

		view.setOnLongClickListener(view1 -> {
			if (onLongClick != null) {
				onLongClick.click((Type) view.getTag());
				return true;
			}
			return false;
		});
	}

	public abstract void bind(Type item);
}
