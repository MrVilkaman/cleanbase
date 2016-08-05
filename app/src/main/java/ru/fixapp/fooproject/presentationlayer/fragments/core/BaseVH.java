package ru.fixapp.fooproject.presentationlayer.fragments.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Zahar on 08.04.16.
 */
public abstract class BaseVH<Type> extends RecyclerView.ViewHolder {

	public BaseVH(View view, MySimpleBaseAdapter.OnClickListener<Type> onClick) {
		super(view);
		ButterKnife.bind(this, view);
		view.setOnClickListener(view1 -> {
			if (onClick != null) {
				onClick.click((Type)view1.getTag());
			}
		});
	}

	public abstract void bind(Type item);
}
