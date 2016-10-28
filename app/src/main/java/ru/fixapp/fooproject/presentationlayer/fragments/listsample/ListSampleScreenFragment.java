package ru.fixapp.fooproject.presentationlayer.fragments.listsample;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityScreenComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.MySimpleAdapter;

public class ListSampleScreenFragment extends BaseFragment<ListSamplePresenter> implements ListSampleView {

	@BindView(R.id.recyclerview) RecyclerView recyclerView;
	private MySimpleAdapter<String> adapter;

	public static ListSampleScreenFragment open() {
		return new ListSampleScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_listsamplescreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

		if (adapter == null) {
			adapter = new MySimpleAdapter<>();
			getPresenter().loadItems();
		}
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void daggerInject() {
		DaggerListSampleScreenComponent.builder()
				.activityScreenComponent(getComponent(ActivityScreenComponent.class))
				.build()
				.inject(this);
	}

	@Override
	public void bingItems(List<String> strings) {
		adapter.setItems(strings);
	}
}