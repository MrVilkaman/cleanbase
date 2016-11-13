package ru.fixapp.fooproject.presentationlayer.fragments.testfrags;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.IToolbar;

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
		toolbar.showIcon(R.drawable.ic_home,() -> showToast(R.string.app_name));
		String text = Integer.toString(getNumber());
		toolbar.setTitle(text);
		numberText.setText(text);
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