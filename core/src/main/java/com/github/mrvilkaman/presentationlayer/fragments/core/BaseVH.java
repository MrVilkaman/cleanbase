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

	@SuppressWarnings("unchecked")
	public void setListeners(final View view,final  ItemListener<Type> onClick,
	                         final ItemListener<Type> onLongClick) {
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view1) {
				if (onClick != null) {
					onClick.click((Type) view.getTag());
				}
			}
		});

		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view1) {
				if (onLongClick != null) {
					onLongClick.click((Type) view.getTag());
					return true;
				}
				return false;
			}
		});
	}

	public abstract void bind(Type item, int position, Set<String> payloads);
}
