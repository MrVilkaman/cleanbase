package com.github.mrvilkaman.presentationlayer.fragments.simplelist;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SimpleListScreenFragment extends BaseFragment<SimpleListPresenter>
		implements SimpleListView {

	@BindView(R.id.recyclerView) RecyclerView recyclerView;
	@Inject SimpleListAdapter adapter;

	public static SimpleListScreenFragment open() {
		return new SimpleListScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_simplelistscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		IToolbar toolbar = getToolbar();
		toolbar.showIcon(android.R.drawable.ic_input_add,() -> getPresenter().add());
		toolbar.showIcon(android.R.drawable.ic_menu_share,() -> getPresenter().shuffle());

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(adapter);
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
		adapter.setItems(item);
	}
}