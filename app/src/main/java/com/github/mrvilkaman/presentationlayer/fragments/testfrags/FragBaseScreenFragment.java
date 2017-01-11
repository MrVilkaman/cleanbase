package com.github.mrvilkaman.presentationlayer.fragments.testfrags;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class FragBaseScreenFragment extends BaseFragment<FragBasePresenter> {

 	@BindView(R.id.frag_text)TextView numberText;

	@Override
	protected int getLayoutId() {
		return R.layout.layout_fragscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		IToolbar toolbar = getToolbar();
		toolbar.show();
		toolbar.showIcon(getIcon(),() -> showToast(R.string.app_name));
		String text = Integer.toString(getNumber());
		toolbar.setTitle(text);
		numberText.setText(text);
	}

	protected int getIcon() {
		return R.drawable.ic_home;
	}

	protected abstract int getNumber();

	protected abstract BaseFragment nextFragment();

	@OnClick(R.id.frag_stack)
	public void onClickStack() {
		getNavigation().showFragment(nextFragment());
	}

	@OnClick(R.id.frag_no_stack)
	public void onClickNoStack() {
		getNavigation().showFragmentWithoutBackStack(nextFragment());
	}

	@OnClick(R.id.frag_root)
	public void onClickRoot() {
		getNavigation().showRootFragment(Frag1ScreenFragment.open());
	}

}