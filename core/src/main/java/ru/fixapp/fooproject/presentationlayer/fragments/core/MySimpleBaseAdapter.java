package ru.fixapp.fooproject.presentationlayer.fragments.core;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Zahar on 17.01.2016.
 */
public abstract class MySimpleBaseAdapter<T, VH extends BaseVH<T>>
		extends RecyclerView.Adapter<VH> {

	protected OnClickListener<T> onClick;

	protected List<T> items;

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		final Context context = parent.getContext();
		final LayoutInflater layoutInflater =
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(getLayoutId(), parent, false);

		return getHolder(view, onClick);
	}

	protected abstract VH getHolder(View view, OnClickListener<T> onClick);

	protected abstract int getLayoutId();

	@Override
	public void onBindViewHolder(VH holder, int position) {
		T item = getItem(position);
		holder.itemView.setTag(item);
		holder.bind(item);
	}

	public T getItem(int pos) {
		return items.get(pos);
	}

	public List<T> getItems() {
		return items != null ? items : Collections.EMPTY_LIST;
	}

	public void setItems(List<T> items) {

		DiffUtil.Callback cb = getDiffCallback(this.items, items);
		if (cb != null) {
			DiffUtil.calculateDiff(cb).dispatchUpdatesTo(this);
			this.items = new ArrayList<>(items);
		} else {
			this.items = new ArrayList<>(items);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getItemCount() {
		return (items != null) ? items.size() : 0;
	}

	public void setOnClick(OnClickListener<T> onClick) {
		this.onClick = onClick;
	}

	protected abstract DiffUtil.Callback getDiffCallback(List<T> oldItems, List<T> newItems);

	public interface OnClickListener<T> {
		void click(T category);
	}
}
