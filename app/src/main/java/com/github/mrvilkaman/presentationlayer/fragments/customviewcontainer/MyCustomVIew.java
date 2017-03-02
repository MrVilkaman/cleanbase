package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseCustomView;
import com.github.mrvilkaman.presentationlayer.utils.UIUtils;

public class MyCustomVIew extends BaseCustomView {

	private View wheelView;
	private Button button;

	public MyCustomVIew(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyCustomVIew(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onViewCreate() {
		button = (Button) findViewById(R.id.button);
		wheelView = findViewById(R.id.progress_wheel);

		button.setOnClickListener(v -> {
			if (wheelView.getVisibility() == View.GONE) {
				showProgress();
			} else {
				hideProgress();
			}
		});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_mycustom_view;
	}

	@Override
	public void hideProgress() {
		UIUtils.changeVisibility(wheelView, false);
	}

	@Override
	public void showProgress() {
		UIUtils.changeVisibility(wheelView, true);
	}

}
