package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.di.CustomWidgetComponent;
import com.github.mrvilkaman.di.INeedInject;
import com.github.mrvilkaman.presentationlayer.activities.MyCustomView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseCustomView;

import javax.inject.Inject;

public class MyCustomWidget extends BaseCustomView<MyCustomPresenter> implements MyCustomView,INeedInject<CustomWidgetComponent> {

	@Inject FormatterForCustomView formatterForCustomView;

	private Button button;
	private TextView textView;

	public MyCustomWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyCustomWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onViewCreate(View inflate, Context context, AttributeSet attrs) {
		button = (Button) inflate.findViewById(R.id.button);
		textView = (TextView) inflate.findViewById(R.id.text);
		button.setOnClickListener(v -> getPresenter().loadValues());
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_mycustom_view;
	}

	@Override
	public void hideProgress() {
		super.hideProgress();
		button.setEnabled(true);
	}

	@Override
	public void showProgress() {
		super.showProgress();
		button.setEnabled(false);
	}

	@Override
	public void changeText() {
		textView.setText(formatterForCustomView.getString());
	}

	@Override
	public void injectMe(CustomWidgetComponent component) {
		component.inject(this);
	}
}
