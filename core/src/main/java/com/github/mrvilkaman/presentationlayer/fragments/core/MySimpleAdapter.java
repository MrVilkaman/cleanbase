package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.view.View;
import android.widget.TextView;

import java.util.Set;

/**
 * Created by Zahar on 17.01.2016.
 */
public class MySimpleAdapter<T> extends MySimpleBaseAdapter<T, MySimpleAdapter.ViewHolder<T>> {

	@Override
	protected ViewHolder<T> getHolder(View view) {
		return new ViewHolder<>(view);
	}

	protected int getLayoutId() {
		return android.R.layout.simple_list_item_1;
	}

	static class ViewHolder<T> extends BaseVH<T> {

		TextView textView;
		ViewHolder(View view) {
			super(view);
			textView = (TextView) view.findViewById(android.R.id.text1);
		}

		@Override
		public void bind(T item, int position, Set<String> payloads) {
			textView.setText(item.toString());

		}
	}
}
