package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mrvilkaman.presentationlayer.utils.ui.UIUtils;

import java.util.List;

@SuppressWarnings("unchecked")
public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int TYPE_HEADER = Integer.MAX_VALUE;
	private static final int TYPE_FOOTER = TYPE_HEADER - 1;
	private MySimpleBaseAdapter adapter;

	private View headerView;
	private View footerView;
	private boolean showFooterAlways;
	private boolean showHeaderAlways;

	public HeaderFooterAdapter(@NonNull MySimpleBaseAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		//if our position is one of our items (this comes from getItemViewType(int position) below)
		if (type != TYPE_HEADER && type != TYPE_FOOTER) {
			return adapter.onCreateViewHolder(parent, type);
		} else {
			FrameLayout frameLayout = new FrameLayout(parent.getContext());
			setHeaderFooterLayoutParams(frameLayout);
			return new HeaderFooterViewHolder(frameLayout);
		}
	}

	protected void setHeaderFooterLayoutParams(ViewGroup viewGroup) {
		ViewGroup.LayoutParams layoutParams;
		layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		viewGroup.setLayoutParams(layoutParams);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position,
								 List<Object> payloads) {
		//check what type of view our position is
		if (isHeader(position)) {
			prepareHeaderFooter((HeaderFooterViewHolder) holder, headerView);
		} else if (isFooter(position)) {
			//			View v = footers.get(position - getRealItemCount() - getHeadersCount());
			//add our view to a footer view and display it
			prepareHeaderFooter((HeaderFooterViewHolder) holder, footerView);
		} else {
			//it's one of our items, display as required
			adapter.onBindViewHolder((BaseVH) holder, position - getHeaderOffset(), payloads);
		}
	}

	protected void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {
		if (view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
		}
		((ViewGroup) vh.itemView).removeAllViews();
		((ViewGroup) vh.itemView).addView(view);
	}

	@Override
	final public int getItemViewType(int position) {

		if (isHeader(position)) {
			return TYPE_HEADER;
		} else if (isFooter(position)) {
			return TYPE_FOOTER;
		}
		return 0;
	}

	public void addFooter(View view) {
		boolean remove = view == null && footerView != null;
		boolean add = view != null && footerView == null;
		boolean update = view != null && footerView != null;
		footerView = view;
		updateFooterHeaderState();
		int itemCount = getItemCount();
		if (add) {
			notifyItemInserted(itemCount);
		} else if (remove) {
			notifyItemRemoved(itemCount);
		} else if (update) {
			notifyItemChanged(itemCount);
		}

	}

	public void showFooterAlways(boolean showFooterAlways){
		this.showFooterAlways = showFooterAlways;
	}
	public void showHeaderAlways(boolean showHeaderAlways){
		this.showHeaderAlways = showHeaderAlways;
	}

	protected void updateFooterHeaderState() {
		boolean show = adapter.getItemCount() != 0;
		if (footerView != null)
			UIUtils.changeVisibility(footerView, show && showFooterAlways);
		if (headerView != null) {
			UIUtils.changeVisibility(headerView, show && showHeaderAlways);
			adapter.setPosOffset(1);
		}else{
			adapter.setPosOffset(0);
		}
	}

	public void addHeader(View view) {
		boolean remove = view == null && headerView != null;
		boolean add = view != null && headerView == null;
		boolean update = view != null && headerView != null;
		headerView = view;
		updateFooterHeaderState();
		if (add) {
			notifyItemInserted(0);
		} else if (remove) {
			notifyItemRemoved(0);
		} else if (update) {
			notifyItemChanged(0);
		}
	}

	private int getHeaderOffset() {
		return hasHeader() ? 1 : 0;
	}

	private boolean hasHeader() {
		return headerView != null;
	}

	private int getFooterOffset() {
		return hasFooter() ? 1 : 0;
	}

	private boolean isHeader(int position) {
		return position == 0 && hasHeader();
	}

	private boolean isFooter(int position) {
		return (getItemCount() - 1 == position) && hasFooter();
	}

	private boolean hasFooter() {
		return footerView != null;
	}

	@Override
	public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
		super.registerAdapterDataObserver(observer);
		adapter.registerAdapterDataObserver(observer);
	}

	@Override
	public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
		super.unregisterAdapterDataObserver(observer);
		adapter.unregisterAdapterDataObserver(observer);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		adapter.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		adapter.onDetachedFromRecyclerView(recyclerView);
	}

	@Override
	public void onViewRecycled(RecyclerView.ViewHolder holder) {
		super.onViewRecycled(holder);
		adapter.onViewRecycled(holder);
	}

	@Override
	public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
		return adapter.onFailedToRecycleView(holder);
	}

	@Override
	public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
		adapter.onViewAttachedToWindow(holder);
	}

	@Override
	public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
		adapter.onViewDetachedFromWindow(holder);
	}

	@Override
	public int getItemCount() {
		return adapter.getItemCount() + getHeaderOffset() + getFooterOffset();
	}

	public <T> void setItems(List<T> items) {
		adapter.setItems(items);
		updateFooterHeaderState();
//		this.notifyDataSetChanged();
	}

	//our header/footer RecyclerView.ViewHolder is just a FrameLayout
	public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {

		public HeaderFooterViewHolder(View itemView) {
			super(itemView);
		}
	}
}
