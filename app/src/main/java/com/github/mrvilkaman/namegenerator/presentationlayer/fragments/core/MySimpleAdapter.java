package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zahar on 17.01.2016.
 */
public class MySimpleAdapter<T> extends MySimpleBaseAdapter<T,MySimpleAdapter.ViewHolder> {

	@Override
	protected ViewHolder getHolder(View view) {

		ViewHolder holder = new ViewHolder(view);
		view.setOnClickListener(view1 -> {
			int position = (int) view1.getTag();
			if (onClick != null) {
				onClick.click(items.get(position));
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		super.onBindViewHolder(holder, position);
		holder.textView.setText(getItem(position).toString());
	}

	protected int getLayoutId() {
		return android.R.layout.simple_list_item_1;
	}

	protected static class ViewHolder extends RecyclerView.ViewHolder {

		@Bind(android.R.id.text1)
		TextView textView;

		public ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
