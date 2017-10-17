package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Zahar on 17.01.2016.
 */
public abstract class MySimpleBaseAdapter<T, VH extends BaseVH<T>>
		extends RecyclerView.Adapter<VH> {

	private static final String TAG = "MySimpleBaseAdapter";
	protected ItemListener<T> onClick;
	protected List<T> items;
	private ItemListener<T> onLongClick;
	private IDiffCallback<T> diffCallback;
	private MyListUpdateCallback updateCallback;
	private int posOffset;

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		final Context context = parent.getContext();
		final LayoutInflater layoutInflater =
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(getLayoutId(), parent, false);
		VH holder = getHolder(view);
		holder.setListeners(view, onClick, onLongClick);
		return holder;
	}

	protected abstract VH getHolder(@NonNull View view);

	protected abstract int getLayoutId();

	public T getItem(int pos) {
		return items.get(pos);
	}

	@NonNull
	public List<T> getItems() {
		return items != null ? items : Collections.EMPTY_LIST;
	}

	public void setItems(@NonNull List<T> items) {
		if (diffCallback == null) {
			diffCallback = createDiffCallback();
		}
		if (diffCallback != null) {
			diffCallback.update(this.items, items);

			if (updateCallback == null) {
				updateCallback = new MyListUpdateCallback(this);
			}
			updateCallback.setPosOffset(posOffset);

			DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(updateCallback);
			this.items = new ArrayList<>(items);
		} else {
			if (this.items == null) {
				this.items = new ArrayList<>(items);
				notifyItemRangeInserted(0, items.size());

			} else {
				this.items = new ArrayList<>(items);
				notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onBindViewHolder(VH holder, int position) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
		T item = getItem(position);
		holder.itemView.setTag(item);
		if (payloads.isEmpty()) {
			holder.bind(item, position, Collections.EMPTY_SET);
		} else {
			holder.bind(item, position, (Set<String>) payloads.get(0));
		}
	}

	@Override
	public int getItemCount() {
		return (items != null) ? items.size() : 0;
	}

	public boolean isEmpty() {
		return getItemCount() == 0;
	}

	public void setOnClick(ItemListener<T> onClick) {
		this.onClick = onClick;
	}

	public void setOnLongClick(ItemListener<T> onLongClick) {
		this.onLongClick = onLongClick;
	}

	@Nullable
	protected IDiffCallback<T> createDiffCallback() {
		return null;
	}


	public void setPosOffset(int posOffset) {
		this.posOffset = posOffset;
	}

	private static class MyListUpdateCallback implements ListUpdateCallback {
		private RecyclerView.Adapter<?> adapter;
		private int posOffset;

		public MyListUpdateCallback(RecyclerView.Adapter<?> adapter) {
			this.adapter = adapter;
		}

		public void setPosOffset(int posOffset) {
			this.posOffset = posOffset;
		}

		@Override
		public void onInserted(int position, int count) {
			adapter.notifyItemRangeInserted(posOffset + position, count);
		}

		@Override
		public void onRemoved(int position, int count) {
			adapter.notifyItemRangeRemoved(posOffset + position, count);
		}

		@Override
		public void onMoved(int fromPosition, int toPosition) {
			adapter.notifyItemMoved(posOffset + fromPosition, posOffset + toPosition);
		}

		@Override
		public void onChanged(int position, int count, Object payload) {
			adapter.notifyItemRangeChanged(posOffset + position, count, payload);
		}
	}
}
