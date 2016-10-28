package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;

public abstract class FragBaseScreenFragment extends BaseFragment<FragBasePresenter> {

 	@BindView(R.id.frag_text)TextView numberText;

	@Override
	protected int getLayoutId() {
		return R.layout.layout_fragscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		numberText.setText(Integer.toString(getNumber()));
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