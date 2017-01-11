package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by Zahar on 08.04.16.
 */
public abstract class BaseVH<Type> extends RecyclerView.ViewHolder {


	public BaseVH(View view) {
		super(view);
		ButterKnife.bind(this, view);
	}

	@Deprecated
	public BaseVH(View view, ItemListener<Type> onClick) {
		this(view);
		ButterKnife.bind(this, view);
		setListeners(view, onClick, null);
	}

	@SuppressWarnings("unchecked")
	final void setListeners(View view, ItemListener<Type> onClick,
							ItemListener<Type> onLongClick) {
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

	@Deprecated
	public void bind(Type item){

	}

	public abstract void bind(Type item, int position, Set<String> payloads);
}
