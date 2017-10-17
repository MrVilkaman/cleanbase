package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Set;


/**
 * Created by Zahar on 08.04.16.
 */
public abstract class BaseVH<Type> extends RecyclerView.ViewHolder {


	public BaseVH(View view) {
		super(view);
	}

	@SuppressWarnings("unchecked")
	public void setListeners(@NonNull final View view, final ItemListener<Type> onClick,
	                         final ItemListener<Type> onLongClick) {
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view1) {
				if (onClick != null) {
					onClick.click(getItem());
				}
			}
		});

		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view1) {
				if (onLongClick != null) {
					onLongClick.click(getItem());
					return true;
				}
				return false;
			}
		});
	}

	@SuppressWarnings({"WeakerAccess", "unchecked"})
	protected Type getItem() {
		return (Type) this.itemView.getTag();
	}

	public abstract void bind(@NonNull Type item, int position, Set<String> payloads);
}
