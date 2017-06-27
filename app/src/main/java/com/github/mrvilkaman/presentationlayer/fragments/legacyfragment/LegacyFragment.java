package com.github.mrvilkaman.presentationlayer.fragments.legacyfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.ActivityCoreComponent;
import com.github.mrvilkaman.di.IHasComponent;
import com.github.mrvilkaman.presentationlayer.fragments.core.IBaseScreen;
import com.github.mrvilkaman.presentationlayer.fragments.longpulling.LongpullingScreenFragment;

public class LegacyFragment extends Fragment implements IBaseScreen {

	private String tag;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.layout_legacy_fragment, container, false);
		inflate.findViewById(R.id.button).setOnClickListener(v -> openScreen());
		return inflate;
	}

	private void openScreen() {

		ActivityCoreComponent component =
				((IHasComponent<ActivityCoreComponent>) getActivity()).getComponent();
		component.getNavigationResolver().showFragment(LongpullingScreenFragment.open());
	}

	@Override
	public String getPreviousFragment() {
		return tag;
	}

	@Override
	public void setPreviousFragment(String tag) {
		this.tag = tag;
	}

	@Override
	public String getName() {
		return LegacyFragment.class.getName();
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	@Override
	public void showProgress() {

	}

	@Override
	public void hideProgress() {

	}
}
