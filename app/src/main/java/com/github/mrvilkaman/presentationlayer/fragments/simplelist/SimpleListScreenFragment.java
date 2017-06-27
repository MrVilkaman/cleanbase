package com.github.mrvilkaman.presentationlayer.fragments.simplelist;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.HeaderFooterAdapter;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SimpleListScreenFragment extends BaseFragment<SimpleListPresenter>
		implements SimpleListView {

	@BindView(R.id.recyclerView) RecyclerView recyclerView;
	@Inject SimpleListAdapter adapter;
	private HeaderFooterAdapter adapterFooter;

	public static SimpleListScreenFragment open() {
		return new SimpleListScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_simplelistscreen_fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		adapterFooter = new HeaderFooterAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		IToolbar toolbar = getToolbar();
		toolbar.showIcon(android.R.drawable.ic_input_add,() -> getPresenter().add());
		toolbar.showIcon(android.R.drawable.ic_menu_share,() -> getPresenter().shuffle());


		TextView header = (TextView) LayoutInflater.from(getContext())
				.inflate(android.R.layout.simple_list_item_1, null);
		header.setText("header");
		TextView footer = (TextView) LayoutInflater.from(getContext())
				.inflate(android.R.layout.simple_list_item_1, null);
		footer.setText("footer");
//		adapterFooter.addHeader(header);
		adapterFooter.addFooter(footer);

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(adapterFooter);
		adapter.setOnClick(category -> getUiResolver().showToast(R.string.cleanbase_simple_text,"Простой клик"));
		adapter.setOnLongClick(category -> getPresenter().remove(category));
	}

	@Override
	public void daggerInject() {
		ActivityComponent component = getComponent(ActivityComponent.class);
		DaggerSimpleListScreenComponent.builder()
				.activityComponent(component)
				.build()
				.inject(this);
	}

	@Override
	public void bind(List<SimpleModel> item) {
		adapterFooter.setItems(item);
	}
}