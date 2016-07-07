package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by Zahar on 17.01.2016.
 */
public class MySimpleAdapter<T> extends MySimpleBaseAdapter<T,MySimpleAdapter.ViewHolder<T>> {

	@Override
	protected ViewHolder<T> getHolder(View view, OnClickListener<T> onClick) {
		return new ViewHolder<>(view,onClick);
	}

	protected int getLayoutId() {
		return android.R.layout.simple_list_item_1;
	}

	static class ViewHolder<T> extends BaseVH<T> {

		@BindView(android.R.id.text1)
		TextView textView;

		ViewHolder(View view, OnClickListener<T> onClick) {
			super(view, onClick);
		}

		@Override
		public void bind(T item) {
			textView.setText(item.toString());
		}
	}
}
